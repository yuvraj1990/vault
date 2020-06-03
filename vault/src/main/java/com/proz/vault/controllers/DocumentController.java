package com.proz.vault.controllers;
import com.proz.vault.data.entities.Document;
import com.proz.vault.data.entities.User;
import com.proz.vault.exceptions.RecordNotFoundException;
import com.proz.vault.modles.AppointmentResponse;
import com.proz.vault.modles.ShareRequest;
import com.proz.vault.services.AppointmentService;
import com.proz.vault.services.DocumentService;
import com.proz.vault.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


/**
 * @author yubraj.singh
 */
@Controller
public class DocumentController {

    @Autowired
    private DocumentService documentService;
    @Autowired
    private UserService userService;
    @Autowired
    private AppointmentService appointmentService;
    private Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @PostMapping("/patient/document")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("appointmentId") Integer appointmentId,
                             @RequestParam("userId") Integer userId,
                             RedirectAttributes attributes) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            attributes.addFlashAttribute("alertClass", "alert-danger");;
            return "redirect:/patient/home";
        }
        try{
            documentService.uploadfile(fileName,appointmentId,userId,file);
        }
        catch (Throwable ex) {
            ex.printStackTrace();
            logger.error("error while uploading file"+fileName+" ex ::"+ex+"for user and appointment"+userId+","+appointmentId);
            attributes.addFlashAttribute("message", "Something went wrong please upload again");
            attributes.addFlashAttribute("alertClass", "alert-danger");;
            return "redirect:/patient/home";
        }
        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');
        attributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/patient/home";
    }

    @GetMapping(value="/patient/document")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> downloadFile(@Param("documentId") int documentId,
                                                          RedirectAttributes attributes) {
        Document document = new Document();
        ByteArrayResource resource = null;
        try {
           document = documentService.getFile(documentId);
           resource = new ByteArrayResource(document.getFileContent());
        }
        catch (RecordNotFoundException ex) {
            logger.error("error while downloding file"+document.getFileName()+" ex ::"+ex);
        }
        catch (Throwable ex) {
            logger.error("error while downloding file"+document.getFileName()+" ex ::"+ex);
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + document.getFileName())
                .contentType(MediaType.APPLICATION_PDF).contentLength(document.getFileContent().length)
                .body(resource);
    }

    @PostMapping("/patient/document/access")
    public String revokeAcess(@RequestParam("appointmentId") int appointmentId,
                             RedirectAttributes attributes) {
        try {
            documentService.removeAccess(appointmentId);
        }
        catch (Throwable ex) {
            ex.printStackTrace();
            logger.error("error while revoking access for  appointmentId "+appointmentId+" ex ::"+ex);
            attributes.addFlashAttribute("message", "error while revking access" +
                    " please retry after some time ");
            attributes.addFlashAttribute("alertClass", "alert-danger");;
            return "redirect:/patient/home";
        }
        attributes.addFlashAttribute("message", "access revoked  successfully uploaded "+ '!');
        attributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/patient/home";
    }

    @GetMapping("/patient/document/share")
    public ModelAndView share(@Param("appointmentId") int appointmentId) {
        ModelAndView model = new ModelAndView();
        User user = userService.getUserByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName());
        List<AppointmentResponse> appointmentDataList = appointmentService.getAppointmentsByUser(user.getId());
        ShareRequest shareRequest = new ShareRequest();
        shareRequest.setAppointmentId(appointmentId);
        appointmentDataList.removeIf(value->value.getAppointmentId()==appointmentId);
        model.addObject("shareRequest",shareRequest);
        model.addObject("appointments", appointmentDataList);
        model.setViewName("user/sharepage");
        return model;
    }

    @RequestMapping(value= {"/patient/document/share"}, method=RequestMethod.POST)
    public String shareDoc(@Valid ShareRequest shareRequest, BindingResult bindingResult,
                           RedirectAttributes attributes) {
        try {
            documentService.shareDocumentsBetween(shareRequest.getAppointmentId(),shareRequest.getIdToshare());
        }
       catch (Throwable ex) {
           ex.printStackTrace();
           logger.error("error while sharing documents for appointmentId "+shareRequest.getAppointmentId()+" ex ::"+ex);
            attributes.addFlashAttribute("message", "error while revking sharing docs" +
                    " please retry after some time ");
            attributes.addFlashAttribute("alertClass", "alert-danger");;
           return "redirect:/patient/home";
       }
        attributes.addFlashAttribute("message", "access revoked  successfully uploaded "+ '!');
        attributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/patient/home";
    }
}
