import com.sap.gateway.ip.core.customdev.util.Message;
import com.sap.it.api.securestore.SecureStoreService;
import com.sap.it.api.securestore.AccessTokenAndUser;
import com.sap.it.api.securestore.exception.SecureStoreException;
import com.sap.it.api.ITApiFactory;
def Message processData(Message message) {

	//Get properties 
	def mapProperties = message.getProperties();	
	def propModel = mapProperties.get("oauth");	
     
    SecureStoreService secureStoreService = ITApiFactory.getService(SecureStoreService.class, null);
 
    AccessTokenAndUser accessTokenAndUser = secureStoreService.getAccesTokenForOauth2AuthorizationCodeCredential("Oauth_S4");
    String token = accessTokenAndUser.getAccessToken();
        
    message.setHeader("Authorization", "bearer "+token);
    
     
    
   return message;
}
