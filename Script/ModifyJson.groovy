import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.*
def Message processData(Message message) {
    
    //Body 
    def body = message.getBody(String.class);
    
    def jsonSlurper = new JsonSlurper();
    def list = jsonSlurper.parseText(body);

    def jsonOP = JsonOutput.toJson([context: list['@odata.context'] , entitiesInPage: list['@des.entitiesInPage'] , processingTimeInMilliseconds: list['@des.processingTimeInMilliseconds'] ,value: list.value]);

    message.setBody(jsonOP)
    return message;
}