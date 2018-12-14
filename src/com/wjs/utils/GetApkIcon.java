package com.wjs.utils;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.util.zip.ZipEntry;  
import java.util.zip.ZipInputStream;  
  
/** 
 * 抽取apk程序的应用图标 
 * @author lichao 
 * Jan 12, 2013 -- 9:51:54 AM 
 */  
public class GetApkIcon 
{  
	public static void getIcon(String apkPath, String iconName, String outPath) 
    {  
        FileInputStream fis = null;  
        FileOutputStream fos = null;  
        ZipInputStream zis = null;  
        File apkFile = new File(apkPath);  
        try 
        {  
            fis = new FileInputStream(apkFile);  
            zis = new ZipInputStream(fis);  
            ZipEntry zipEntry = null;  
            while((zipEntry = zis.getNextEntry()) != null){  
                String name = zipEntry.getName().toLowerCase();  
                if((name.endsWith("/" + iconName) && name.contains("drawable") && name.contains("res")) || (name.endsWith("/" + iconName) && name.contains("raw") && name.contains("res"))||(name.endsWith("/" + iconName) && name.contains("mipmap") && name.contains("res"))){  
                	fos = new FileOutputStream(new File(outPath));  
                    byte[] buffer = new byte[1024];  
                    int n = 0;  
                    while((n = zis.read(buffer, 0, buffer.length)) != -1){  
                        fos.write(buffer, 0, n);  
                    }  
                    break;  
                }  
            }  
            zis.closeEntry(); 
            zis.close();
            zipEntry = null;  
        }
        catch (Exception e) 
        {  
            e.printStackTrace();  
        }
        finally 
        {  
            try 
            {  
                if (zis != null) 
                {  
                    zis.close();  
                }  
                if (fis != null) 
                {  
                    fis.close();  
                }  
                if(fos!=null)
                {
                	fos.close();
                }
            } 
            catch (Exception e) 
            {  
                e.printStackTrace();  
            }  
        }  
    }  
}  