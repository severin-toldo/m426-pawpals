package com.ateam.paw_pals.model.api;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileUploadResponse {
	private String fileName;
    private String contentType;
    private long size;
}
