package com.zoom.util.email;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@Setter
public class MessageHtmlUtil {
	
	
    
	// usado por AutoCad
    public static String msgAutoCadHtml(String token) {
        
    	String body;

        body = "<body style=\"background-color: #f4f4f4; margin: 0 !important; padding: 0 !important;\">\n" +
                "    <!-- HIDDEN PREHEADER TEXT -->\n" +
                "    <div style=\"display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: 'Lato', Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\"> We're thrilled to have you here! Get ready to dive into your new account.\n" +
                "    </div>\n" +
                "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "        <!-- LOGO -->\n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#00bde1\" align=\"center\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                    <tr>\n" +
                "                        <td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\"> </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#00bde1\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                    <tr>\n" +
                "                        <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\">\n" +
                "                            <h1 style=\"font-size: 48px; font-weight: 400; margin: 2;\">Bem vindo ao Zoom App!</h1>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                    <tr>\n" +
                "                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                            <p style=\"margin: 0;\">Por favor, insira o código abaixo na página de confirmação para continuar o seu Cadastro!</p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td bgcolor=\"#ffffff\" align=\"left\">\n" +
                "                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                <tr>\n" +
                "                                    <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 20px 30px 60px 30px;\">\n" +
                "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                            <tr>\n" +
                "                                                <td align=\"center\" style=\"border-radius: 3px;\" bgcolor=\"#ffffff\"><h1 style=\"font-size: 20px; font-family: Helvetica, Arial, sans-serif; color: #010101; text-decoration: none; color: #010101; text-decoration: none; padding: 15px 25px; border-radius: 2px; border: 1px solid black; display: inline-block;\">\n" +
                "                                                    "+token+"</a></td>\n" +
                "                                            </tr>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </table>\n" +
                "                        </td>\n" +
                "                    </tr> <!-- COPY -->\n" +
                "                    <tr>\n" +
                "                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                            <p style=\"margin: 0;\">Abraços,<br>TimeZoom</p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 30px 10px 0px 10px;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                    <tr>\n" +
                "                        <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 30px 30px 30px 30px; border-radius: 4px 4px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                            <h2 style=\"font-size: 20px; font-weight: 400; color: #111111; margin: 0;\">Precisa de ajuda?</h2>\n" +
                "                            <h2 style=\"font-size: 20px; font-weight: 400; color: #111111; margin: 0;\">Envie um e-mail:</h2>\n" +
                "                            <a href=\"mailto:murakami.edson@gmail.com\" target=\"_blank\" rel=\"noreferrer noopener\"><strong>murakami.edson@gmail.com</strong></a>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</body>\n" +
                "\n" +
                "</html>";

        return HEADER+body;
    }
    
 // usado por Esqueci a Senha
    public static String msgEsqueciSenhaHtml(String token, String contextPath) {
    	
        String nomexhtml = "ResetSenha.xhtml";
        
        log.info("<a href=" + contextPath + "/unrestricted/" + nomexhtml + "?token=" + token + ">AQUI</a>");
        
    	String body = 
    			
          "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
          + "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
          + "<head>"
          + " <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"
          + " <title>Redefinição de Senha </title>"
          + " <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>"
          + "</head>"
          + "<body style=\"margin: 0; padding: 0;\">"
          + "<font face=\"Arial\"> </font>"
          + "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">"
          + " <tr>"
          + "  <td align=\"center\" bgcolor=\"#000000\" style=\"padding: 15px 0 15px 0;\">"         
          + "    </td>"
          + "    </tr>"
          + "     <tr>"
          + "     <td bgcolor=\"#FAD267\" style=\"padding: 20px 30px 50px 30px;\">"
          + "      <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
          + "       <tr>"
          + "    <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">"
          + "               <p style=\"font-size:18px\">"
          + "         Olá tudo bem?"
          + "     </p>"
          + "    </td>"
          + "   </tr>"
          + "   <tr>"
          + "    <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">"
          + "     <p style=\"font-size:18px\">"
          + "       Você solicitou a redefinição de senha. Clique "
          + "       <a href=" + contextPath + "/unrestricted/" + nomexhtml + "?token=" + token + ">AQUI</a>"
          + "       para trocar a senha. O token gerado expira em 15 minutos."
          + "     </p>      "
          + "     <p style=\"font-size:18px\">"
          + "       Se tiver dúvidas ou precisar de ajuda acesse nossos canais de contato abaixo."
          + "     </p>"
          + "    </td>"
          + "   </tr>"
          + "   <tr>"
          + "    <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">"
          + "    <p style=\"font-size:18px\">"
          + "       Att."
          + "     </p>"
          + "     <p style=\"font-size:18px\">"
          + "       Atendimento."
          + "     </p>"
          + "    </td>"
          + "   </tr>"
          + "  </table>"
          + " </td>"
          + "</tr>"
          + "<tr>"
          + " <td bgcolor=\"#00BCE1\" style=\"padding: 30px 30px 30px 30px;\" >"
          + "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
          + "    <tr>"
          + "      <td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\">"
          + "       IFSP. Salto-SP, 2025<br/>"
          + "      </td>"
          + "    </tr>"
          + "   </table>"
          + " </td>"
          + "</tr>"
        + " </table>"
       + "</body>"
       + "</html>";

        return body;
    }

    
    
    
    
    
    
    /********************************
     * Cabecalho
     *******************************/
    private static String HEADER = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "\n" +
            "<head>\n" +
            "    <title></title>\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
            "    <style type=\"text/css\">\n" +
            "        @media screen {\n" +
            "            @font-face {\n" +
            "                font-family: 'Lato';\n" +
            "                font-style: normal;\n" +
            "                font-weight: 400;\n" +
            "                src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');\n" +
            "            }\n" +
            "\n" +
            "            @font-face {\n" +
            "                font-family: 'Lato';\n" +
            "                font-style: normal;\n" +
            "                font-weight: 700;\n" +
            "                src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');\n" +
            "            }\n" +
            "\n" +
            "            @font-face {\n" +
            "                font-family: 'Lato';\n" +
            "                font-style: italic;\n" +
            "                font-weight: 400;\n" +
            "                src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');\n" +
            "            }\n" +
            "\n" +
            "            @font-face {\n" +
            "                font-family: 'Lato';\n" +
            "                font-style: italic;\n" +
            "                font-weight: 700;\n" +
            "                src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        body,\n" +
            "        table,\n" +
            "        td,\n" +
            "        a {\n" +
            "            -webkit-text-size-adjust: 100%;\n" +
            "            -ms-text-size-adjust: 100%;\n" +
            "        }\n" +
            "\n" +
            "        table,\n" +
            "        td {\n" +
            "            mso-table-lspace: 0pt;\n" +
            "            mso-table-rspace: 0pt;\n" +
            "        }\n" +
            "\n" +
            "        img {\n" +
            "            -ms-interpolation-mode: bicubic;\n" +
            "        }\n" +
            "\n" +
            "        /* RESET STYLES */\n" +
            "        img {\n" +
            "            border: 0;\n" +
            "            height: auto;\n" +
            "            line-height: 100%;\n" +
            "            outline: none;\n" +
            "            text-decoration: none;\n" +
            "        }\n" +
            "\n" +
            "        table {\n" +
            "            border-collapse: collapse !important;\n" +
            "        }\n" +
            "\n" +
            "        body {\n" +
            "            height: 100% !important;\n" +
            "            margin: 0 !important;\n" +
            "            padding: 0 !important;\n" +
            "            width: 100% !important;\n" +
            "        }\n" +
            "\n" +
            "        /* iOS BLUE LINKS */\n" +
            "        a[x-apple-data-detectors] {\n" +
            "            color: inherit !important;\n" +
            "            text-decoration: none !important;\n" +
            "            font-size: inherit !important;\n" +
            "            font-family: inherit !important;\n" +
            "            font-weight: inherit !important;\n" +
            "            line-height: inherit !important;\n" +
            "        }\n" +
            "\n" +
            "        /* MOBILE STYLES */\n" +
            "        @media screen and (max-width:600px) {\n" +
            "            h1 {\n" +
            "                font-size: 32px !important;\n" +
            "                line-height: 32px !important;\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        /* ANDROID CENTER FIX */\n" +
            "        div[style*=\"margin: 16px 0;\"] {\n" +
            "            margin: 0 !important;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n";
}
