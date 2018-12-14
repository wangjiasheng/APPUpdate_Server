package com.wjs.appupdate;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.sun.jmx.remote.internal.NotificationBuffer;
import com.wjs.utils.DecoderEncoderUtils;
import com.wjs.utils.StringUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;

public class PushMessage extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String undecode = request. getParameter("path");
        String tag=request.getParameter("tag");
        //String filepath= DecoderEncoderUtils.decodeFilepath(undecode);
        String downUrl=request.getScheme()+"://"+getIp(request)+":"+request.getServerPort()+request.getContextPath()+"/"+"Down?path="+undecode;
        System.out.println(downUrl);

        String tempPath = this.getServletContext().getRealPath("/WEB-INF/log4j.properties");
        System.setProperty("log4j.configuration", tempPath);
        JPushClient jpushClient = new JPushClient("2b1cf9d64df9aa4325e24f7b", "4908df9a19f7438ba8911171", null, ClientConfig.getInstance());
        PushPayload payload = buildAndroidUpdate(downUrl,tag);
        push(jpushClient,payload,out);
        out.flush();
        out.close();
    }
    public void push(JPushClient jpushClient,PushPayload payload,PrintWriter out){
        try {
            PushResult result = jpushClient.sendPush(payload);
            if(result.isResultOK()){
                out.print(true);
            }
            else{
                out.print(false);
            }
            jpushClient.close();
        } catch (APIConnectionException e) {
        } catch (APIRequestException e) {
        }
    }
    public PushPayload buildPushObject_all_all_alert(){
       return PushPayload.alertAll("哈哈");
    }
    public PushPayload buildAndroidUpdate(String downUrl,String... tag){
        PushPayload.Builder builder=PushPayload.newBuilder();
        builder.setPlatform(Platform.all());
        if(tag!=null&&tag.length>0){
            Audience audience=Audience.newBuilder().addAudienceTarget(AudienceTarget.tag(tag)).build();
            builder.setAudience(audience);
        } else {
            builder.setAudience(Audience.all());
        }
        builder.setMessage(Message.newBuilder().setTitle("叮当医生").setMsgContent("叮当医生").addExtra("download_url", downUrl).build());
        //builder.setOptions(Options.newBuilder().setApnsProduction(false).build()); //生产环境
        //builder.setNotification(buildNotifacation());
        PushPayload pushPayload=builder.build();
        return pushPayload;
    }
    public Notification buildNotifacation(){
        Notification.Builder buffer=Notification.newBuilder();
        buffer.addPlatformNotification(buildAndroidNotifacation());
        Notification notification=buffer.build();
        return notification;
    }
    public AndroidNotification buildAndroidNotifacation(){
        return AndroidNotification.newBuilder().addExtra("type","infomation").setAlert("叮当医生更新了").build();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
    private String getIp(HttpServletRequest request){
        return request.getServerName();
    }
}
