package com.quanlihocsinh.util;

import javax.servlet.http.Part;
import java.io.InputStream;
import java.util.Base64;

public class FileUploadUtil {
    private static final int MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final String[] ALLOWED_TYPES = { "image/jpeg", "image/png", "image/gif", "image/webp" };

    /**
     * Chuyển đổi Part (file upload) thành Base64 string
     */
    public static String convertPartToBase64(Part part) throws Exception {
        if (part == null || part.getSize() == 0) {
            return null;
        }

        // Kiểm tra kích thước file
        if (part.getSize() > MAX_FILE_SIZE) {
            throw new Exception("File quá lớn. Kích thước tối đa: 5MB");
        }

        // Kiểm tra loại file
        String contentType = part.getContentType();
        boolean isAllowed = false;
        for (String type : ALLOWED_TYPES) {
            if (contentType.equals(type)) {
                isAllowed = true;
                break;
            }
        }

        if (!isAllowed) {
            throw new Exception("Loại file không được phép. Chỉ chấp nhận: JPEG, PNG, GIF, WebP");
        }

        try (InputStream inputStream = part.getInputStream()) {
            byte[] fileContent = new byte[(int) part.getSize()];
            inputStream.read(fileContent);
            return "data:" + contentType + ";base64," + Base64.getEncoder().encodeToString(fileContent);
        }
    }

    /**
     * Kiểm tra xem string có phải Base64 image data không
     */
    public static boolean isBase64Image(String str) {
        return str != null && str.startsWith("data:image/");
    }
}
