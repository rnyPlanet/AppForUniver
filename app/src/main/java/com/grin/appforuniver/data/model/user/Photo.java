package com.grin.appforuniver.data.model.user;

import java.util.Arrays;

import lombok.Data;

@Data
class Photo {

    /*
    photo": {
        "id": 1,
        "photo": null
    }
    */

    private Integer id;
    private byte[] photo;

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
