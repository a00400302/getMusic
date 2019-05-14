package com.example.a0040.music.Beans;

import java.util.List;

public class MusicBeanX {
    private String type;
    private boolean success;
    private int total;
    private AlbumBean album;
    private String name;
    private String id;
    private boolean needPay;
    private String file;
    private List<ArtistsBean> artists;


    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }



    public AlbumBean getAlbum() {
        return album;
    }

    public void setAlbum(AlbumBean album) {
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isNeedPay() {
        return needPay;
    }

    public void setNeedPay(boolean needPay) {
        this.needPay = needPay;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<ArtistsBean> getArtists() {
        return artists;
    }

    public void setArtists(List<ArtistsBean> artists) {
        this.artists = artists;
    }

     public static class AlbumBean {
        private String id;
        private String name;
        private String cover;
        private String coverBig;
        private String coverSmall;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCoverBig() {
            return coverBig;
        }

        public void setCoverBig(String coverBig) {
            this.coverBig = coverBig;
        }

        public String getCoverSmall() {
            return coverSmall;
        }

        public void setCoverSmall(String coverSmall) {
            this.coverSmall = coverSmall;
        }
    }

     public static class ArtistsBean {
        private String id;
        private String name;
         private String avatar;
         private List<?> tns;
         private List<?> alias;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public List<?> getTns() {
            return tns;
        }

        public void setTns(List<?> tns) {
            this.tns = tns;
        }

        public List<?> getAlias() {
            return alias;
        }

        public void setAlias(List<?> alias) {
            this.alias = alias;
        }


    }

}


