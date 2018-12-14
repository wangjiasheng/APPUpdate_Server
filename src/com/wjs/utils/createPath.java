package com.wjs.utils;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import com.nothome.delta.Delta;
import com.nothome.delta.DiffWriter;
import com.nothome.delta.GDiffWriter;
public class createPath 
{
	public static boolean createPatch(String newapk,String oldapk,String patch) {  
        try 
        {  
            DiffWriter output = null;  
            File sourceFile = null;  
            File targetFile = null;  
  
            sourceFile = new File(oldapk);  
            targetFile = new File(newapk);  
            output = new GDiffWriter(new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(patch)))));  
            if (sourceFile.length() > Integer.MAX_VALUE || targetFile.length() > Integer.MAX_VALUE) 
            {  
                System.err.println("source or target is too large, max length is "+ Integer.MAX_VALUE);  
                System.err.println("aborting..");  
            }  
            Delta d = new Delta();  
            d.compute(sourceFile, targetFile, output);  
            return true;
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return false;
    }  
	
}
