package it.unitn.shoppinglesto.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class UtilityHelper {

    /**
     * Return the root url of the application
     * @param request
     * @return root url of the application
     */
    public static String getURLWithContextPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

    }

    public static String getCookieValue(HttpServletRequest request, String templistcookiename) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(templistcookiename.equals(cookie.getName()))
                    return cookie.getValue();
            }

        }
        return null;
    }


    /**
     * return the filename of a file passed in a multipart form
     * @param part
     * @return the name of the file
     */
    public static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim()
                        .replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1)
                        .substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    public static String renameImage(String originalName, String newName){
        return newName + originalName.substring(originalName.lastIndexOf(".", originalName.length()));
    }


    public static String uploadFileToDirectory(String uploadDirectory, String filename, Part filePart) throws IOException{
        if(!Files.exists(Paths.get(uploadDirectory)))
            Files.createDirectories(Paths.get(uploadDirectory));
        File file = new File(uploadDirectory, filename);
        final String absolutePath = file.getAbsolutePath();
        filePart.write(absolutePath);
        return absolutePath;
    }

}
