<?php

require_once('src/Exception.php');
require_once('src/PHPMailer.php');
require_once('src/SMTP.php');

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;

$mail = new PHPMailer(true);

try{
    $mail->SMTPDebug = SMTP::DEBUG_SERVER;
    $mail->isSMTP();
    $mail->Host = 'smtp.gmail.com';
    $mail->SMTPAuth = true;
    $mail->Username = 'coorujainfo@gmail.com';
    $mail->Password = 'INFO42019.1';
    $mail->Port - 587;

    $mail->setFrom('coorujainfo@gmail.com');
    $mail->setFrom('arthurhenriq@gmail.com');

    $mail->isHTML(true);
    $mail->Subject = 'Teste de email via gmail Thuko';
    $emai->Body = 'Chegou o email do <strong>THUKO</strong>';
    $mail->AltBody = 'Chegou o email do THUKO';

    if ($mail->send()) {
        echo 'Email enviado';
    } else {
        echo 'Email nao enviado';
    }

} catch (Exception $e) {
    echo "Erro ao enviar mensagem: {$mail->ErrorInfo}";
}