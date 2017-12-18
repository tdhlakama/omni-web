package tk.omi.model;

import javax.persistence.*;

@Entity
public class CustomerDocument extends BaseEntityId {

    public static String COPY_ID = "COPY OF ID";
    public static String PROOF_OF_RESIDENCE = "PROOF OF RESIDENCE";
    public static String SIGNATURE = "SIGNATURE";

    @ManyToOne
    private Customer customer;
    private String documentType;
    private String fileName;
    @Lob
    @Column(columnDefinition = "mediumblob")
    private byte[] document;
    @Transient
    private Long accountNumber;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}