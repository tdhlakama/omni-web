package tk.omi.controller;

public class ServerResponse {

    public ServerResponse() {
    }

    public ServerResponse(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    private Long id;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
