package com.feri.pay.pockectstatepay_api.controller;

import com.pockectstate.api.common.util.Qrcode_Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;

/**
 *@Author feri
 *@Date Created in 2019/7/25 15:53
 */
@Controller
public class QrcodeController {
    //生成二维码 内容为普通文本
    @GetMapping("/payapi/createqrcode.do")
    public void createQrCode(String msg, HttpServletResponse response) throws IOException {
        BufferedImage bufferedImage=Qrcode_Util.createQrCode(msg,400);
        ImageIO.write(bufferedImage,"png",response.getOutputStream());
    }
    //生成二维码 内容为特殊字符 比如 / : =  编码 base64URL
    @GetMapping("/payapi/createqr/{msg}")
    public void creteQr(@PathVariable String msg,HttpServletResponse response) throws IOException {
        String m=new String(Base64.getUrlDecoder().decode(msg));
        ImageIO.write(Qrcode_Util.createQrCode(m,400),"png",response.getOutputStream());
    }
}