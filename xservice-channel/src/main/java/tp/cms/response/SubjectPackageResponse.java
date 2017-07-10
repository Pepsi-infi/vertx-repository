package tp.cms.response;


import java.util.List;

public class SubjectPackageResponse {

    private Integer code;
    private Packeges data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Packeges getData() {
        return data;
    }

    public void setData(Packeges data) {
        this.data = data;
    }

    public static class Packeges {
        private List<ContentItem> dataList;
        private Integer id;
        private String name;
        private Integer porde;
        private Integer ptype;
        private Integer showSubscript;
        private Integer status;
        private Integer tjId;

        public List<ContentItem> getDataList() {
            return dataList;
        }

        public void setDataList(List<ContentItem> dataList) {
            this.dataList = dataList;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPorde() {
            return porde;
        }

        public void setPorde(Integer porde) {
            this.porde = porde;
        }

        public Integer getPtype() {
            return ptype;
        }

        public void setPtype(Integer ptype) {
            this.ptype = ptype;
        }

        public Integer getShowSubscript() {
            return showSubscript;
        }

        public void setShowSubscript(Integer showSubscript) {
            this.showSubscript = showSubscript;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getTjId() {
            return tjId;
        }

        public void setTjId(Integer tjId) {
            this.tjId = tjId;
        }

    }
}
