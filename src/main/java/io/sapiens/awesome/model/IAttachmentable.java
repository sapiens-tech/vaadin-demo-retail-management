package io.sapiens.awesome.model;

import org.springframework.web.multipart.MultipartFile;

public interface IAttachmentable {

  long UPLOAD_MAX_FILESIZE = 1048576;

  String UPLOAD_ALLOWED_EXTENSIONS = ".jpg, .jpeg, .gif, .png, .xlsx, .xls, .doc, .docx, pdf";

  String getId();

  MultipartFile getAttachment();

  void setAttachment(MultipartFile attachment);

  Boolean getImageUploaded();

  void setImageUploaded(Boolean imageUploaded);

  Boolean getImageToDelete();

  void setImageToDelete(Boolean imageToDelete);

  String getFileName();

  String getContentType();
}
