package ca.ubc.cs304.model;

public class VehicleTypeModel extends IModel {
    private final String vtname;
    private final String features;
    private final int wrate;
    private final int drate;
    private final int hrate;
    private final int wirate;
    private final int dirate;
    private final int hirate;
    private final int krate;

    @Override
    public String[] getTuple() {
        String[] ret = {vtname, features, Integer.toString(this.wrate), Integer.toString(this.drate), Integer.toString(this.hrate)
                , Integer.toString(this.wirate), Integer.toString(this.dirate), Integer.toString(this.hirate), Integer.toString(this.krate)};
        return ret;
    }

    public VehicleTypeModel(String vtname, String features, int wrate, int drate, int hrate,
                            int wirate, int dirate, int hirate, int krate) {
        this.vtname = vtname;
        this.features = features;
        this.wrate = wrate;
        this.drate = drate;
        this.hrate = hrate;
        this.wirate = wirate;
        this.dirate = dirate;
        this.hirate = hirate;
        this.krate = krate;
        table_name = "VehicleType";
    }

    public String getVtname() {
        return vtname;
    }

    public String getFeatures() {
        return features;
    }

    public int getWrate() {
        return wrate;
    }

    public int getDrate() {
        return drate;
    }

    public int getHrate() {
        return hrate;
    }

    public int getWirate() {
        return wirate;
    }

    public int getDirate() {
        return dirate;
    }

    public int getHirate() {
        return hirate;
    }

    public int getKrate() {
        return krate;
    }
}
