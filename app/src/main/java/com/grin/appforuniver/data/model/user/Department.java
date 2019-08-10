package com.grin.appforuniver.data.model.user;

import lombok.Data;

@Data
class Department {

    /*
    department": {
        "id": 2,
        "pkeyTypeDepartment": null,
        "pkeyBoss": null,
        "name": "Деканат факультету компютерних наук",
        "emeil": null,
        "telefon": null
    }
    */

    private Integer id;
    private Integer pkeyTypeDepartment;
    private Integer pkeyBoss;
    private String name;
    private String emeil;
    private String telefon;

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", pkeyTypeDepartment=" + pkeyTypeDepartment +
                ", pkeyBoss=" + pkeyBoss +
                ", name='" + name + '\'' +
                ", emeil='" + emeil + '\'' +
                ", telefon='" + telefon + '\'' +
                '}';
    }
}
