package tk.omi.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.audit.listener.AbstractAuditListener;
import org.springframework.stereotype.Component;

@Component
public class AuditEventListener extends AbstractAuditListener {

    private static final Logger LOG = LoggerFactory.getLogger(AuditEventListener.class);

    @Autowired
    private AuditEventRepository auditEventRepository;

    @Override
    protected void onAuditEvent(AuditEvent event) {

        LOG.info("On audit event: timestamp: {}, principal: {}, type: {}, data: {}",
                event.getTimestamp(),
                event.getPrincipal(),
                event.getType(),
                event.getData()
        );

        auditEventRepository.add(event);
    }
}
