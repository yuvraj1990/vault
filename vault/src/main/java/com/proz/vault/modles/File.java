package com.proz.vault.modles;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yubraj.singh
 */
@Data
@AllArgsConstructor
public class File {
    private String name;
    private String link;
    private int id ;
}
