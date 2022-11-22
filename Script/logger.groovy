import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date;
def Message log01(Message message) {processData("log01.InitialMessage", message);}
def Message log02(Message message) {processData("log02.HTTPRequest", message);}
def Message log03(Message message) {processData("log03.HTTPResponse", message);}
def Message log04(Message message) {processData("log04.FinalMessage", message);}
def Message log05(Message message) {processData("log05.Exception", message);}

def Message processData(String prefix, Message message) {
	def headers = message.getHeaders();
	def body = message.getBody(java.lang.String) as String;
	def messageLog = messageLogFactory.getMessageLog(message);
	for (header in headers) {
	   messageLog.setStringProperty("header." + header.getKey().toString(), header.getValue().toString())
	}
	for (property in properties) {
	   messageLog.setStringProperty("property." + property.getKey().toString(), property.getValue().toString())
	}
    if(messageLog != null){
        messageLog.addAttachmentAsString(prefix, body, "text/plain");
     }
    return message;
}