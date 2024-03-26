package com.tsh.sd43.infrastructure.configs.mail;

import com.tsh.sd43.entity.HoaDon;
import com.tsh.sd43.entity.HoaDonChiTiet;
import com.tsh.sd43.repository.IHoaDonChiTietRepo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.NumberFormat;

import org.thymeleaf.context.Context;

import java.util.ArrayList;

@RestController
@CrossOrigin("*")
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    private IHoaDonChiTietRepo hoaDonChiTietRepo;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-email")
    public String sendEmail(@RequestBody HoaDon hoaDon) {
        String pattern = "HH:mm dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Context context = new Context();
        ArrayList<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepo.findBillDetailsByCodeBill(hoaDon.getMa());
        String date = simpleDateFormat.format(hoaDonChiTiets.get(0).getNgayTao());
        String trangThaiHoaDon = hoaDon.getTrangThai() == 1 ?
                "Tạo hóa đơn" : hoaDon.getTrangThai() == 2 ?
                "Chờ giao" : hoaDon.getTrangThai() == 3 ?
                "Đang giao" : hoaDon.getTrangThai() == 4 ?
                "Thành công" : hoaDon.getTrangThai() == 5 ?
                "Bị hủy" : "Trả hàng";

        context.setVariable("bill", hoaDon);
        context.setVariable("billDetails", hoaDonChiTiets);
        context.setVariable("ngayTao", date );
        context.setVariable("trangThaiHoaDon", trangThaiHoaDon);

        if(hoaDon.getTrangThai() == 1){
            emailService.sendEmailWithHtmlTemplate(hoaDon.getEmail(), "Đơn hàng " + hoaDon.getMa() + "  được tạo thành công!","bill-template", context);
        }else if(hoaDon.getTrangThai() == 2){
            emailService.sendEmailWithHtmlTemplate(hoaDon.getEmail(), "Đơn hàng " + hoaDon.getMa() + " đang được người bán giao cho bên vận chuyển!","bill-template", context);
        }else if(hoaDon.getTrangThai() == 3){
            emailService.sendEmailWithHtmlTemplate(hoaDon.getEmail(), "Đơn hàng " + hoaDon.getMa() + " đang trên đường giao đến bạn! ","bill-template", context);
        }else if(hoaDon.getTrangThai() == 4){
            emailService.sendEmailWithHtmlTemplate(hoaDon.getEmail(), "Đơn hàng " + hoaDon.getMa() + " đã hoàn thành!","bill-template", context);
        }else if(hoaDon.getTrangThai() == 5){
            emailService.sendEmailWithHtmlTemplate(hoaDon.getEmail(), "Đơn hàng " + hoaDon.getMa() + " đã bị hủy!","bill-template", context);
        }else if(hoaDon.getTrangThai() == 6){
            emailService.sendEmailWithHtmlTemplate(hoaDon.getEmail(), "Đơn hàng " + hoaDon.getMa() + " đã được trả lại!","bill-template", context);
        }
        return "Email sent successfully!";
    }

//    @PostMapping("/send-bill-html-email")
//    public String sendHtmlEmail(@RequestBody EmailRequest emailRequest) {
//        List<Account> khachHang = accountRepository.sendMailAccount();
//
//        Context context = new Context();
//        context.setVariable("message", emailRequest.getBody());
//        for (Account account : khachHang) {
//            emailService.sendEmailWithHtmlTemplate(account.getEmail(), "Phiếu giảm giá mới dành cho quý khách hàng của BeePhoneShop", "email-template", context);
//        }
//        return "HTML email sent successfully!";
//    }

//    @PostMapping("/send-html-email/voucher")
//    public String sendHtmlEmailVoucher(@RequestBody SendVoucherEmailRequest request) {
//        Context context = new Context();
//        List<Account> khachHang = accountRepository.sendMailAccount();
//        for (Account account1 : khachHang) {
//            context.setVariable("fullNameCustomer", account1.getHoVaTen());
//        }
//        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
//        String formattedGiaTriVoucher = currencyFormatter.format(request.getGiaTriVoucher());
//
//        NumberFormat currencyFormatter1 = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
//        String formattedConditionVoucher = currencyFormatter1.format(request.getDieuKienApDung());
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy"); // Định dạng mong muốn
//        String formattedDate = dateFormat.format(request.getStartTime()); // Chuyển đổi Date thành chuỗi theo định dạng
//
//
//        SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy"); // Định dạng mong muốn
//        String formattedDate1 = dateFormat1.format(request.getEndTime()); // Chuyển đổi Date thành chuỗi theo định dạng
//
//
//        context.setVariable("nameVoucher", request.getTen());
//        context.setVariable("valueVoucher", formattedGiaTriVoucher);
//        context.setVariable("conditionVoucher", formattedConditionVoucher);
//        context.setVariable("codeVoucher", request.getMa());
//        context.setVariable("startTime", formattedDate);
//        context.setVariable("endTime", formattedDate1);
//
//        for (Account account : khachHang) {
//            emailService.sendEmailWithHtmlTemplateVoucher(account.getEmail(), "Phiếu giảm giá mới dành cho quý khách hàng của BeePhoneShop", "template-voucher", context);
//        }
//        return "HTML email sent successfully!";
//    }

//    @PostMapping("/send-html-email-get-pass")
//    public String sendHtmlEmailGetPass(@RequestBody ForgetPassRequest forgetPassRequest) {
//        Account account = accountClientRepository.findByEmail(forgetPassRequest.getEmail());
//
//        if (account == null) {
//            throw new RuntimeException("Không tìm thấy tài khoản");
//        }
//        Context context = new Context();
//
//        //create new pass
//        String newPass = "Abc123.";
//        account.setMatKhau(passwordEncoder.encode(newPass));
//        accountClientRepository.save(account);
//        //send new pass
//        context.setVariable("password", newPass);
//
//        emailService.sendEmailWithHtmlTemplate(forgetPassRequest.getEmail(), "Mật khẩu của bạn", "email-get-pass-template", context);
//        return "HTML email sent successfully!";
//    }
}
