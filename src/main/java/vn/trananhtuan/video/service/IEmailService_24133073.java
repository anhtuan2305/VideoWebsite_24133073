package vn.trananhtuan.video.service;

public interface IEmailService_24133073 {

    String generateOTP();

    boolean sendOTP(
            String recipientEmail,
            String otpCode
    );
}