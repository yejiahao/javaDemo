package org.yejh.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.Date;
import java.util.Properties;

public class MailUtil {
    private static final Logger LOG = LoggerFactory.getLogger(MailUtil.class);

    private static final String READ_PATH = "C:/Users/Ye Jiahao/Desktop/eml/用户邮件地址变更.eml";
    private static final String WRITE_PATH = "C:/Users/Ye Jiahao/Desktop/eml/testEml.eml";
    private static final String IMAGE_PATH = "C:/Users/Ye Jiahao/Desktop/eml/cr3057.png";
    private static final String ATTACH_PATH = "C:/Users/Ye Jiahao/Desktop/eml/附件文档.docx";
    private static final String CHARSET = "UTF-8";

    private static String senderAddress = "454696086@qq.com";
    private static String senderSMTPPassword = "****************";
    private static String senderSMTPHost = "smtp.qq.com";

    private static Address[] recipients = new InternetAddress[4];

    private MailUtil() {
        throw new AssertionError();
    }


    public static void main(String[] args) throws IOException, MessagingException {
        recipients[0] = new InternetAddress("454696086@qq.com", "收件人1", CHARSET);
        recipients[1] = new InternetAddress("244102959@qq.com", "收件人2", CHARSET);
        recipients[2] = new InternetAddress("jiahye@coremail.cn", "抄送人1", CHARSET);
        recipients[3] = new InternetAddress("admin@yejh.cn", "密送人1", CHARSET);

        readMail(READ_PATH);

        writeMail(WRITE_PATH);

        sendMail();
    }

    private static void readMail(String readPath) throws IOException, MessagingException {
        Properties props = System.getProperties();

        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        LOG.info("---------- readMail start ----------");
        MimeMessage message = createMessage(session, readPath, senderAddress, recipients);
        LOG.info("邮件主题： {}", message.getSubject());
        LOG.info("发件人地址： {}", ((InternetAddress) message.getFrom()[0]).getAddress());
        LOG.info("发件人名称： {}", ((InternetAddress) message.getFrom()[0]).getPersonal());
        LOG.info("邮件正文： {}", message.getContent());
        LOG.info("---------- readMail end   ----------");
    }

    private static void writeMail(String writePath) throws IOException, MessagingException {
        Properties props = System.getProperties();

        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        LOG.info("---------- writeMail start ----------");
        MimeMessage message = createMessage(session, null, senderAddress, recipients);
        OutputStream os = new FileOutputStream(writePath);
        message.writeTo(os);
        os.close();
        LOG.info("---------- writeMail end   ----------");
    }

    private static void sendMail() throws MessagingException, IOException {
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", senderSMTPHost);
        props.put("mail.smtp.auth", "true");

        // A secure connection is requiered(such as ssl).
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.socketFactory.port", "465");

        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        LOG.info("---------- sendMail start ----------");
        MimeMessage message = createMessage(session, null, senderAddress, recipients);
        Transport transport = session.getTransport();
        transport.connect(senderAddress, senderSMTPPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        LOG.info("---------- sendMail end   ----------");
    }

    private static MimeMessage createMessage(Session session, String readPath, String senderAddress, Address[] recipients) throws IOException, MessagingException {
        MimeMessage message;
        if (readPath != null) {
            InputStream is = new FileInputStream(readPath);
            message = new MimeMessage(session, is);
        } else {
            message = new MimeMessage(session);
            message.setSubject("主题subject", CHARSET);
            message.setFrom(new InternetAddress(senderAddress, "小admin", CHARSET));

            message.setRecipient(Message.RecipientType.TO, recipients[0]);
            message.addRecipient(Message.RecipientType.TO, recipients[1]);
            message.setRecipient(Message.RecipientType.CC, recipients[2]);
            message.setRecipient(Message.RecipientType.BCC, recipients[3]);

            // 创建图片“节点”
            MimeBodyPart image = new MimeBodyPart();
            DataHandler dh = new DataHandler(new FileDataSource(IMAGE_PATH));// 读取本地文件
            image.setDataHandler(dh);// 将图片数据添加到“节点”
            image.setContentID("image_fairy_tail");// 为“节点”设置一个唯一编号（在文本“节点”将引用该ID）
            // 创建文本“节点”
            MimeBodyPart text = new MimeBodyPart();
            // 这里添加图片的方式是将整个图片包含到邮件内容中，实际上也可以以http链接的形式添加网络图片
            text.setContent("这是一张图片<br/><img src='cid:image_fairy_tail'/>", "text/html;charset=" + CHARSET);
            // （文本+图片）设置文本和图片“节点”的关系（将文本和图片“节点”合成一个混合“节点”）
            MimeMultipart mm_text_image = new MimeMultipart();
            mm_text_image.addBodyPart(text);
            mm_text_image.addBodyPart(image);
            mm_text_image.setSubType("related");// 关联关系
            // 将文本+图片的混合“节点”封装成一个普通“节点”
            // 最终添加到邮件的Content是由多个BodyPart组成的Multipart，上面的mm_text_image并非BodyPart，所以要把mm_text_image封装成一个BodyPart
            MimeBodyPart text_image = new MimeBodyPart();
            text_image.setContent(mm_text_image);
            // 创建附件“节点”
            MimeBodyPart attachment = new MimeBodyPart();
            DataHandler dh2 = new DataHandler(new FileDataSource(ATTACH_PATH));// 读取本地文件
            attachment.setDataHandler(dh2);// 将附件数据添加到“节点”
            attachment.setFileName(MimeUtility.encodeText(dh2.getName()));// 设置附件的文件名（需要编码）
            // 设置（文本+图片）和附件的关系（合成一个大的混合“节点”/Multipart）
            MimeMultipart mm = new MimeMultipart();
            mm.addBodyPart(text_image);
            mm.addBodyPart(attachment);// 如果有多个附件，可以创建多个多次添加
            mm.setSubType("mixed");// 混合关系
            // 设置整个邮件的关系（将最终的混合“节点”作为邮件的内容添加到邮件对象）
            message.setContent(mm);

            // message.setContent("正文content", "text/html;charset=" + CHARSET);

            message.setSentDate(new Date());
            message.saveChanges();
        }
        return message;
    }
}
