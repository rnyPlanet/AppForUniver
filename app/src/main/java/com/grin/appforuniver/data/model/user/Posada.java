package com.grin.appforuniver.data.model.user;

class Posada {

    /*
    sprPosada": {
        "id": 1,
        "postVykl": "професор, доктор наук"
    }
    */

    private int id;
    private String postVykl;

    @Override
    public String toString() {
        return "Posada{" +
                "id=" + id +
                ", postVykl='" + postVykl + '\'' +
                '}';
    }
}
