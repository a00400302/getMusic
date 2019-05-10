package com.example.a0040.music;

import java.util.List;

public class MusicBean {


    public MusicBean() {
    }

    public MusicBean(boolean success, int total, List<SongListBean> songList) {
        this.success = success;
        this.total = total;
        this.songList = songList;
    }

    /**
     * success : true
     * total : 428
     *
     */



    private boolean success;
    private int total;
    private List<SongListBean> songList;

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

    public List<SongListBean> getSongList() {
        return songList;
    }

    public void setSongList(List<SongListBean> songList) {
        this.songList = songList;
    }

    public static class SongListBean {
        /**
         * album : {"id":78766275,"name":"元气夏天","cover":"https://p1.music.126.net/SmNAEToWfwkxHDhS2g92vw==/109951164033232934.jpg?param=250y250","coverBig":"https://p1.music.126.net/SmNAEToWfwkxHDhS2g92vw==/109951164033232934.jpg?param=400y400","coverSmall":"https://p1.music.126.net/SmNAEToWfwkxHDhS2g92vw==/109951164033232934.jpg?param=140y140"}
         * artists : [{"id":840134,"name":"刘瑞琦","tns":[],"alias":[]}]
         * name : 元气夏天
         * id : 1361239052
         * needPay : true
         */

        private AlbumBean album;
        private String name;
        private int id;
        private boolean needPay;
        private List<ArtistsBean> artists;

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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isNeedPay() {
            return needPay;
        }

        public void setNeedPay(boolean needPay) {
            this.needPay = needPay;
        }

        public List<ArtistsBean> getArtists() {
            return artists;
        }

        public void setArtists(List<ArtistsBean> artists) {
            this.artists = artists;
        }

        public static class AlbumBean {
            /**
             * id : 78766275
             * name : 元气夏天
             * cover : https://p1.music.126.net/SmNAEToWfwkxHDhS2g92vw==/109951164033232934.jpg?param=250y250
             * coverBig : https://p1.music.126.net/SmNAEToWfwkxHDhS2g92vw==/109951164033232934.jpg?param=400y400
             * coverSmall : https://p1.music.126.net/SmNAEToWfwkxHDhS2g92vw==/109951164033232934.jpg?param=140y140
             */

            private int id;
            private String name;
            private String cover;
            private String coverBig;
            private String coverSmall;

            public int getId() {
                return id;
            }

            public void setId(int id) {
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
            /**
             * id : 840134
             * name : 刘瑞琦
             * tns : []
             * alias : []
             */

            private int id;
            private String name;
            private List<?> tns;
            private List<?> alias;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
}
