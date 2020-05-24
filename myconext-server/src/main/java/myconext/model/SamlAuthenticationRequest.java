package myconext.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Document(collection = "authentication_requests")
@Getter
@NoArgsConstructor
public class SamlAuthenticationRequest {

    @Id
    private String id;

    private String requestId;

    private String issuer;

    private String consumerAssertionServiceURL;

    private String relayState;

    private String hash;

    private Date expiresIn;

    private String userId;

    private String requesterEntityId;

    private String authenticationContextClassReferenceValue;

    private boolean rememberMe;

    private String rememberMeValue;

    public SamlAuthenticationRequest(String requestId, String issuer, String consumerAssertionServiceURL,
                                     String relayState, String requesterEntityId, String authenticationContextClassReferenceValue) {
        this.id = UUID.randomUUID().toString();
        this.requestId = requestId;
        this.issuer = issuer;
        this.consumerAssertionServiceURL = consumerAssertionServiceURL;
        this.relayState = relayState;
        this.expiresIn = Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant());
        this.requesterEntityId = requesterEntityId;
        this.authenticationContextClassReferenceValue = authenticationContextClassReferenceValue;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void setRememberMeValue(String rememberMeValue) {
        this.rememberMeValue = rememberMeValue;
    }

}
