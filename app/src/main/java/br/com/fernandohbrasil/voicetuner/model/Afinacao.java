package br.com.fernandohbrasil.voicetuner.model;

public class Afinacao {

    public Afinacao() {
    }

    public int getNotaId(float freq) {
        int grupoid = getNotaGrupo(freq);

        switch (grupoid) {
            case 1:
                return findNotaIdGp1(freq);
            case 2:
                return findNotaIdGp2(freq);
            case 3:
                return findNotaIdGp3(freq);
            case 4:
                return findNotaIdGp4(freq);
            case 5:
                //return findNotaIdGp5(freq);
                return 0;
            default:
                return 0;
        }
    }


    private int getNotaGrupo(float freq) {
        if ((freq > 65.00) && (freq <= 127.14180)) {
            return 1;
        } else if ((freq > 127.14180) && (freq <= 254.28358)) {
            return 2;
        } else if ((freq > 254.28358) && (freq <= 508.56720)) {
            return 3;
        } else if ((freq > 508.56720) && (freq <= 1017.13434)) {
            return 4;
        } else if ((freq > 1017.13434) && (freq <= 1975.60)) {
            return 5;
        } else {
            return 0;
        }
    }

    private int findNotaIdGp1(float freq) {
        if ((freq > 65.00) && (freq <= 67.35101)) {
            return 24;
        } else if ((freq > 67.35101) && (freq <= 71.35592)) {
            return 25;
        } else if ((freq > 71.35592) && (freq <= 75.59897)) {
            return 26;
        } else if ((freq > 75.59897) && (freq <= 80.09431)) {
            return 27;
        } else if ((freq > 80.09431) && (freq <= 84.85696)) {
            return 28;
        } else if ((freq > 84.85696) && (freq <= 89.90283)) {
            return 29;
        } else if ((freq > 89.90283) && (freq <= 95.24873)) {
            return 30;
        } else if ((freq > 95.24873) && (freq <= 100.91251)) {
            return 31;
        } else if ((freq > 100.91251) && (freq <= 106.91309)) {
            return 32;
        } else if ((freq > 106.91309) && (freq <= 113.27047)) {
            return 33;
        } else if ((freq > 113.27047) && (freq <= 120.00588)) {
            return 34;
        } else {
            return 35;
        }
    }

    private int findNotaIdGp2(float freq) {
        if ((freq > 127.14180) && (freq <= 134.70205)) {
            return 36;
        } else if ((freq > 134.70205) && (freq <= 142.71185)) {
            return 37;
        } else if ((freq > 142.71185) && (freq <= 151.19793)) {
            return 38;
        } else if ((freq > 151.19793) && (freq <= 160.18864)) {
            return 39;
        } else if ((freq > 160.18864) && (freq <= 169.71394)) {
            return 40;
        } else if ((freq > 169.71394) && (freq <= 179.80566)) {
            return 41;
        } else if ((freq > 179.80566) && (freq <= 190.49746)) {
            return 42;
        } else if ((freq > 190.49746) && (freq <= 201.82503)) {
            return 43;
        } else if ((freq > 201.82503) && (freq <= 213.82617)) {
            return 44;
        } else if ((freq > 213.82617) && (freq <= 226.54092)) {
            return 45;
        } else if ((freq > 226.54092) && (freq <= 240.01174)) {
            return 46;
        } else {
            return 47;
        }
    }

    private int findNotaIdGp3(float freq) {
        if ((freq > 254.28358) && (freq <= 269.40408)) {
            return 48;
        } else if ((freq > 269.40408) && (freq <= 285.42369)) {
            return 49;
        } else if ((freq > 285.42369) && (freq <= 302.39586)) {
            return 50;
        } else if ((freq > 302.39586) && (freq <= 320.37726)) {
            return 51;
        } else if ((freq > 320.37726) && (freq <= 339.42789)) {
            return 52;
        } else if ((freq > 339.42789) && (freq <= 359.61131)) {
            return 53;
        } else if ((freq > 359.61131) && (freq <= 380.99489)) {
            return 54;
        } else if ((freq > 380.99489) && (freq <= 403.65004)) {
            return 55;
        } else if ((freq > 403.65004) && (freq <= 427.65234)) {
            return 56;
        } else if ((freq > 427.65234) && (freq <= 453.08189)) {
            return 57;
        } else if ((freq > 453.08189) && (freq <= 480.02354)) {
            return 58;
        } else {
            return 59;
        }
    }

    private int findNotaIdGp4(float freq) {
        if ((freq > 508.56720) && (freq <= 538.80817)) {
            return 60;
        } else if ((freq > 538.80817) && (freq <= 570.84738)) {
            return 61;
        } else if ((freq > 570.84738) && (freq <= 604.79172)) {
            return 62;
        } else if ((freq > 604.79172) && (freq <= 640.75452)) {
            return 63;
        } else if ((freq > 640.75452) && (freq <= 678.85580)) {
            return 64;
        } else if ((freq > 678.85580) && (freq <= 719.22266)) {
            return 65;
        } else if ((freq > 719.22266) && (freq <= 761.98984)) {
            return 66;
        } else if ((freq > 761.98984) && (freq <= 807.30011)) {
            return 67;
        } else if ((freq > 807.30011) && (freq <= 855.30469)) {
            return 68;
        } else if ((freq > 855.30469) && (freq <= 906.16379)) {
            return 69;
        } else {
            return 70;
        }
    }

    private int findNotaIdGp5(float freq) {
        if ((freq > 960.04709) && (freq <= 1017.13434)) {
            return 71;
        } else if ((freq > 1017.13434) && (freq <= 1077.61633)) {
            return 72;
        } else if ((freq > 1077.61633) && (freq <= 1141.69482)) {
            return 73;
        } else if ((freq > 1141.69482) && (freq <= 1209.58350)) {
            return 74;
        } else if ((freq > 1209.58350) && (freq <= 1281.50909)) {
            return 75;
        } else if ((freq > 1281.50909) && (freq <= 1357.71161)) {
            return 76;
        } else if ((freq > 1357.71161) && (freq <= 1438.44525)) {
            return 77;
        } else if ((freq > 1438.44525) && (freq <= 1523.97968)) {
            return 78;
        } else if ((freq > 1523.97968) && (freq <= 1614.60028)) {
            return 79;
        } else if ((freq > 1614.60028) && (freq <= 1710.60938)) {
            return 80;
        } else if ((freq > 1710.60938) && (freq <= 1812.32739)) {
            return 81;
        } else if ((freq > 1812.32739) && (freq <= 1920.09406)) {
            return 82;
        } else {
            return 83;
        }

    }
}
