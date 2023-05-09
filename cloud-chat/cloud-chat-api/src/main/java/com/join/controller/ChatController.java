package com.join.controller;

import com.join.service.ChatService;
import com.join.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author join
 * @Description
 * @date 2023/2/27 20:07
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    /**
     * 获取和当前对象的聊天记录
     */
    @GetMapping("/history")
    public Result getHistory(@RequestParam("currentChatID") String currentChatID){
        return Result.success(chatService.getHistory(Long.parseLong(currentChatID)));
    }

    /**
     * 获取所有聊天表情表情
     * @return
     */
    @GetMapping("/emoji")
    public Result getEmoji(){
        return chatService.getEmoji();
    }


    /**
     * 上传音频
     * @param file
     * @return
     */
    @PostMapping("/uploadAudio")
    public Result uploadAudio(MultipartFile file, HttpServletRequest request){
        return chatService.uploadAudio(file,request.getParameter("fileName"));
    }


    @GetMapping("/isOnline")
    public Result isOnline(@RequestParam("id") String id){
        return chatService.isOnline(Long.parseLong(id));
    }


    @PostMapping("/uploadFile")
    public Result uploadFile(MultipartFile file,HttpServletRequest request){
        return chatService.uploadFile(file,request.getParameter("name"));
    }

}
