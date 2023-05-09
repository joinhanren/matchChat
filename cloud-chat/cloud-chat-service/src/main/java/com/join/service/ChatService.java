package com.join.service;

import com.join.vo.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author join
 * @Description
 * @date 2023/3/1 20:04
 */
public interface ChatService {
    /**
     * 获取和当前对象的聊天记录
     */
    public Result getHistory(Long currentChatID);

    /**
     * 获取表情
     * @return
     */
    public Result getEmoji();

    /**
     * 上传语音
     * @param file
     * @return
     */
    public Result uploadAudio(MultipartFile file,String fileName);

    /**
     * 上传文件
     * @param file
     * @param fileName
     * @return
     */
    public Result uploadFile(MultipartFile file, String fileName);

    /**
     * 当前好友是否在线
     */
    public Result isOnline(Long id);


}
