package com.jsmsframework.jsms;

import com.jsmsframework.jsms.dto.PackageDTO;

/**
 */
public interface Generate {
    
    public void generate(PackageDTO packageDTO, String template);
}
