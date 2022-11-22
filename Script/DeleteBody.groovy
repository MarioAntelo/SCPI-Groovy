import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonOutput

Message processData(Message message) {
    def body = "";
    message.setBody(body) 
    return message;
}
