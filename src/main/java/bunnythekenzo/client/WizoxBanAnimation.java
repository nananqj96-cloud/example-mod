package bunnythekenzo.client;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;

public final class WizoxBanAnimation {
  public static final float amount = 136.6F;
  public static final long timestamp = 32000L;
  public static final float amount2 = (float) (Math.PI / 180.0);
  public static final float[][] amountArray =
      new float[][] {
        {0.0F, 0.303F},
        {5.0F, 2.498F},
        {13.0F, 6.015F},
        {21.0F, 9.537F},
        {29.0F, 13.064F},
        {33.0F, 14.825F},
        {41.0F, 18.347F},
        {46.0F, 20.596F},
        {53.0F, 23.605F},
        {61.0F, 27.117F},
        {69.0F, 30.624F},
        {73.0F, 32.38F}
      };
  public static final CollectionData2[] collectionData2Array = choreography();

  public static CollectionData2[] choreography() {
    ArrayList arrayList = new ArrayList();
    EmotePose emotePose = new EmotePose();
    EmotePose emotePose2 =
        new EmotePose()
            .getCollectionSupport2(-88.0F, -18.0F, 9.0F, -8.0F)
            .getEmotePose2(-12.0F, 8.0F, -35.0F, -72.0F)
            .getCollectionSupport2(8.0F, -12.0F, -6.0F, -5.0F, -12.0F, 5.0F)
            .getCollectionSupport2(-0.7F, 0.0F, 0.0F);
    EmotePose emotePose3 =
        new EmotePose()
            .getCollectionSupport2(-60.0F, -52.0F, -12.0F, -78.0F)
            .getEmotePose2(-66.0F, 52.0F, 12.0F, -82.0F)
            .getCollectionSupport22(-28.0F, 8.0F, 48.0F, -28.0F, -8.0F, 48.0F)
            .getCollectionSupport2(14.0F, 0.0F, 0.0F, -10.0F, 0.0F, 0.0F);
    EmotePose emotePose4 =
        new EmotePose()
            .getCollectionSupport2(-42.0F, -15.0F, 92.0F, -18.0F)
            .getEmotePose2(-48.0F, 15.0F, -92.0F, -15.0F)
            .getCollectionSupport22(-12.0F, 18.0F, 16.0F, -12.0F, -18.0F, 16.0F)
            .getCollectionSupport2(-9.0F, 0.0F, 0.0F, 7.0F, 0.0F, 0.0F);
    EmotePose emotePose5 =
        new EmotePose()
            .getCollectionSupport2(-30.0F, -15.0F, 28.0F, -65.0F)
            .getEmotePose2(-30.0F, 15.0F, -28.0F, -65.0F)
            .getCollectionSupport22(-48.0F, 16.0F, 86.0F, -48.0F, -16.0F, 86.0F)
            .getCollectionSupport2(22.0F, 0.0F, 0.0F, -12.0F, 0.0F, 0.0F);
    render(arrayList, -1.0F, emotePose);
    render(
        arrayList,
        0.0F,
        emotePose.getEmotePose().getCollectionSupport2(4.0F, -9.0F, -5.0F, -6.0F, -28.0F, 7.0F));
    render(
        arrayList,
        0.75F,
        emotePose
            .getEmotePose()
            .getCollectionSupport2(-22.0F, -12.0F, 25.0F, -110.0F)
            .getCollectionSupport2(7.0F, 12.0F, 7.0F, -8.0F, 24.0F, -7.0F));
    render(
        arrayList,
        1.75F,
        emotePose2.getEmotePose().getCollectionSupport2(-35.0F, -10.0F, 18.0F, -100.0F));
    render(arrayList, 2.0F, emotePose2);
    render(arrayList, 2.6F, emotePose2);
    render(
        arrayList,
        2.8F,
        emotePose.getEmotePose().getCollectionSupport2(-26.0F, 0.0F, 22.0F, -95.0F));
    render(
        arrayList,
        3.0F,
        emotePose2.getEmotePose().getCollectionSupport2(10.0F, -19.0F, -9.0F, -8.0F, -18.0F, 8.0F));
    render(
        arrayList,
        3.65F,
        emotePose2.getEmotePose().getCollectionSupport2(-75.0F, 24.0F, 44.0F, -15.0F));
    render(
        arrayList,
        4.4F,
        emotePose5
            .getEmotePose()
            .getCollectionSupport2(-20.0F, 0.0F, 50.0F, -105.0F)
            .getEmotePose2(-20.0F, 0.0F, -50.0F, -105.0F));
    render(arrayList, 5.0F, emotePose3);
    render(arrayList, 5.5F, emotePose4.getEmotePose().getCollectionSupport2(0.0F, 0.0F, 1.3F));
    render(
        arrayList,
        6.0F,
        emotePose4
            .getEmotePose()
            .getCollectionSupport2(5.0F, -18.0F, -15.0F, -8.0F, -12.0F, 10.0F)
            .getCollectionSupport2(-1.5F, 0.0F, 0.0F));
    render(
        arrayList,
        6.75F,
        emotePose5.getEmotePose().getCollectionSupport2(16.0F, 16.0F, 12.0F, -10.0F, 10.0F, -8.0F));
    render(
        arrayList,
        7.0F,
        emotePose2
            .getEmotePose()
            .getEmotePose2(-95.0F, 12.0F, -38.0F, -12.0F)
            .getCollectionSupport2(-20.0F, 0.0F, 38.0F, -95.0F)
            .getCollectionSupport2(1.4F, 0.0F, 0.0F));
    render(
        arrayList,
        7.5F,
        emotePose4.getEmotePose().getCollectionSupport2(-120.0F, -10.0F, 45.0F, -25.0F));
    render(
        arrayList,
        8.0F,
        emotePose3.getEmotePose().getCollectionSupport2(10.0F, -20.0F, -8.0F, -8.0F, 12.0F, 7.0F));
    render(
        arrayList,
        8.75F,
        emotePose3.getEmotePose().getCollectionSupport2(10.0F, 20.0F, 8.0F, -8.0F, -12.0F, -7.0F));
    render(
        arrayList,
        9.0F,
        emotePose4
            .getEmotePose()
            .getCollectionSupport2(-150.0F, 0.0F, 20.0F, -12.0F)
            .getEmotePose2(-12.0F, 0.0F, -40.0F, -90.0F));
    render(
        arrayList,
        9.5F,
        emotePose4
            .getEmotePose()
            .getEmotePose2(-150.0F, 0.0F, -20.0F, -12.0F)
            .getCollectionSupport2(-12.0F, 0.0F, 40.0F, -90.0F));
    render(arrayList, 10.0F, emotePose5.getEmotePose().getCollectionSupport2(0.0F, 0.0F, 0.0F));
    render(arrayList, 10.5F, emotePose4.getEmotePose().getCollectionSupport2(0.0F, 0.0F, 1.8F));
    render(
        arrayList,
        11.0F,
        emotePose3.getEmotePose().getCollectionSupport2(8.0F, -24.0F, -10.0F, -7.0F, 20.0F, 8.0F));
    render(
        arrayList,
        12.5F,
        emotePose3.getEmotePose().getCollectionSupport2(8.0F, -24.0F, -10.0F, -7.0F, 20.0F, 8.0F));
    render(arrayList, 12.75F, emotePose5);
    render(
        arrayList,
        13.0F,
        getEmotePose(false, 1.0F)
            .getCollectionSupport2(-75.0F, -10.0F, 50.0F, -28.0F)
            .getEmotePose2(24.0F, 0.0F, -18.0F, -70.0F));
    render(arrayList, 13.5F, getEmotePose(true, 0.35F).getCollectionSupport2(-1.5F, 0.3F, 0.6F));
    render(
        arrayList,
        14.0F,
        getEmotePose(true, 1.0F)
            .getCollectionSupport2(15.0F, 0.0F, 24.0F, -60.0F)
            .getEmotePose2(-72.0F, 15.0F, -45.0F, -24.0F));
    render(arrayList, 14.75F, emotePose5.getEmotePose().getCollectionSupport2(1.2F, 0.0F, 0.0F));
    render(
        arrayList,
        15.0F,
        getEmotePose(false, 1.25F)
            .getCollectionSupport2(-100.0F, -24.0F, 25.0F, -16.0F)
            .getCollectionSupport2(2.1F, -0.5F, 0.5F));
    render(
        arrayList,
        15.5F,
        getEmotePose(true, 0.6F)
            .getEmotePose2(-105.0F, 20.0F, -20.0F, -20.0F)
            .getCollectionSupport2(1.0F, -0.3F, 0.4F));
    render(
        arrayList,
        16.0F,
        getEmotePose(false, 0.65F)
            .getCollectionSupport2(-35.0F, 0.0F, 60.0F, -110.0F)
            .getCollectionSupport2(-0.5F, 0.0F, 0.5F));
    render(
        arrayList,
        17.0F,
        getEmotePose(true, 1.3F)
            .getEmotePose2(-35.0F, 0.0F, -60.0F, -110.0F)
            .getCollectionSupport2(-2.0F, 0.0F, 0.7F));
    render(arrayList, 17.75F, emotePose3.getEmotePose().getCollectionSupport2(-1.2F, 0.0F, 0.0F));
    render(
        arrayList,
        18.0F,
        getEmotePose(false, 0.75F).getCollectionSupport2(-55.0F, -30.0F, 65.0F, -35.0F));
    render(arrayList, 18.5F, getEmotePose(true, 0.9F).getEmotePose2(-60.0F, 30.0F, -70.0F, -30.0F));
    render(
        arrayList,
        19.0F,
        getEmotePose(false, 1.0F).getCollectionSupport2(-115.0F, 0.0F, 30.0F, -30.0F));
    render(
        arrayList, 19.5F, getEmotePose(true, 0.55F).getEmotePose2(-100.0F, 0.0F, -35.0F, -20.0F));
    render(
        arrayList,
        20.0F,
        getEmotePose(false, 0.7F).getCollectionSupport2(-55.0F, -25.0F, 42.0F, -75.0F));
    render(arrayList, 20.5F, getEmotePose(true, 0.6F).getEmotePose2(-65.0F, 20.0F, -38.0F, -70.0F));
    render(arrayList, 20.75F, emotePose5);
    render(arrayList, 21.0F, getCollectionSupport2(false));
    render(arrayList, 21.6F, getCollectionSupport2(false));
    render(
        arrayList,
        22.0F,
        emotePose5.getEmotePose().getCollectionSupport2(20.0F, -10.0F, 10.0F, -10.0F, 8.0F, -8.0F));
    render(arrayList, 22.5F, getCollectionSupport2(true).getCollectionSupport2(1.8F, 0.0F, 0.8F));
    render(arrayList, 23.0F, getCollectionSupport2(true).getCollectionSupport2(1.8F, 0.0F, 0.0F));
    render(
        arrayList,
        23.75F,
        emotePose3.getEmotePose().getCollectionSupport2(12.0F, 20.0F, 0.0F, -8.0F, -15.0F, 0.0F));
    render(
        arrayList,
        24.0F,
        emotePose4
            .getEmotePose()
            .getCollectionSupport2(-160.0F, -10.0F, 28.0F, -25.0F)
            .getEmotePose2(-145.0F, 10.0F, -36.0F, -30.0F)
            .getCollectionSupport2(0.0F, 0.0F, 1.5F));
    render(
        arrayList,
        24.5F,
        emotePose5
            .getEmotePose()
            .getCollectionSupport2(-15.0F, -15.0F, 50.0F, -115.0F)
            .getEmotePose2(-15.0F, 15.0F, -50.0F, -115.0F));
    render(
        arrayList,
        25.0F,
        getCollectionSupport2(false)
            .getCollectionSupport22(-58.0F, 14.0F, 75.0F, -12.0F, -15.0F, 22.0F));
    render(
        arrayList,
        25.5F,
        getCollectionSupport2(true)
            .getCollectionSupport22(-12.0F, 15.0F, 22.0F, -58.0F, -14.0F, 75.0F));
    render(
        arrayList,
        26.0F,
        emotePose3.getEmotePose().getCollectionSupport2(18.0F, -18.0F, -12.0F, -8.0F, 8.0F, 10.0F));
    render(
        arrayList,
        26.75F,
        emotePose5
            .getEmotePose()
            .getCollectionSupport2(25.0F, 18.0F, 12.0F, -15.0F, -8.0F, -10.0F));
    render(
        arrayList,
        27.0F,
        emotePose4
            .getEmotePose()
            .getCollectionSupport2(-80.0F, 0.0F, 100.0F, -5.0F)
            .getEmotePose2(-80.0F, 0.0F, -100.0F, -5.0F));
    render(arrayList, 27.5F, emotePose2.getEmotePose().getCollectionSupport2(-1.0F, 0.0F, 0.0F));
    render(arrayList, 28.0F, emotePose3);
    render(arrayList, 28.65F, emotePose3);
    EmotePose emotePose6 =
        emotePose2
            .getEmotePose()
            .getEmotePose2(-145.0F, 10.0F, -32.0F, -18.0F)
            .getCollectionSupport2(-8.0F, -25.0F, -16.0F, 5.0F, 20.0F, 12.0F);
    render(arrayList, 29.0F, emotePose6);
    render(arrayList, 29.8F, emotePose6);
    render(
        arrayList,
        30.0F,
        emotePose6
            .getEmotePose()
            .getCollectionSupport2(-3.0F, -25.0F, -12.0F, -9.0F, 20.0F, 12.0F));
    render(arrayList, 30.5F, emotePose6);
    render(arrayList, 32.0F, emotePose6);
    render(arrayList, 32.75F, emotePose5);
    render(
        arrayList,
        33.0F,
        emotePose4
            .getEmotePose()
            .getCollectionSupport2(-20.0F, 0.0F, 55.0F, -60.0F)
            .getEmotePose2(-20.0F, 0.0F, -55.0F, -60.0F)
            .getCollectionSupport2(-16.0F, 0.0F, 0.0F, 8.0F, 0.0F, 0.0F));
    render(
        arrayList,
        34.0F,
        emotePose
            .getEmotePose()
            .getCollectionSupport2(-40.0F, -20.0F, 35.0F, -90.0F)
            .getEmotePose2(-40.0F, 20.0F, -35.0F, -90.0F)
            .getCollectionSupport2(6.0F, 0.0F, -8.0F, -18.0F, 0.0F, 8.0F));
    render(
        arrayList,
        35.0F,
        emotePose5.getEmotePose().getCollectionSupport2(28.0F, 0.0F, 0.0F, -12.0F, 0.0F, 0.0F));
    render(
        arrayList,
        36.0F,
        emotePose
            .getEmotePose()
            .getCollectionSupport2(-8.0F, 12.0F, 10.0F, 8.0F, -10.0F, -8.0F)
            .getCollectionSupport2(1.2F, 0.7F, 0.0F));
    render(
        arrayList,
        37.0F,
        emotePose4
            .getEmotePose()
            .getCollectionSupport2(-25.0F, 0.0F, 115.0F, -75.0F)
            .getEmotePose2(-10.0F, 0.0F, -70.0F, -8.0F)
            .getCollectionSupport2(4.0F, -10.0F, -12.0F, -5.0F, -20.0F, 8.0F));
    render(
        arrayList,
        37.75F,
        emotePose4
            .getEmotePose()
            .getCollectionSupport2(-20.0F, 0.0F, 85.0F, -20.0F)
            .getEmotePose2(-20.0F, 0.0F, -85.0F, -30.0F)
            .getCollectionSupport2(-10.0F, 0.0F, 0.0F, 6.0F, 0.0F, 0.0F));
    render(
        arrayList,
        38.5F,
        emotePose4
            .getEmotePose()
            .getCollectionSupport2(-10.0F, 0.0F, 70.0F, -8.0F)
            .getEmotePose2(-25.0F, 0.0F, -115.0F, -75.0F)
            .getCollectionSupport2(4.0F, 10.0F, 12.0F, -5.0F, 20.0F, -8.0F));
    render(
        arrayList,
        39.5F,
        emotePose5
            .getEmotePose()
            .getCollectionSupport2(23.0F, 18.0F, 12.0F, -10.0F, -12.0F, -8.0F));
    render(
        arrayList,
        40.0F,
        emotePose2
            .getEmotePose()
            .getEmotePose2(-80.0F, 20.0F, -45.0F, -10.0F)
            .getCollectionSupport2(-15.0F, 0.0F, 35.0F, -85.0F));
    render(arrayList, 40.75F, emotePose5);
    EmotePose emotePose7 =
        emotePose3
            .getEmotePose()
            .getCollectionSupport2(-95.0F, -15.0F, 45.0F, -85.0F)
            .getEmotePose2(-15.0F, 20.0F, -65.0F, -90.0F)
            .getCollectionSupport2(10.0F, 22.0F, 10.0F, -8.0F, -28.0F, -8.0F);
    render(arrayList, 41.0F, emotePose7);
    render(arrayList, 42.25F, emotePose7);
    render(
        arrayList,
        42.75F,
        emotePose7
            .getEmotePose()
            .getCollectionSupport2(-20.0F, -20.0F, 65.0F, -90.0F)
            .getEmotePose2(-95.0F, 15.0F, -45.0F, -85.0F)
            .getCollectionSupport2(10.0F, -22.0F, -10.0F, -8.0F, 28.0F, 8.0F));
    render(
        arrayList,
        43.75F,
        emotePose7
            .getEmotePose()
            .getCollectionSupport2(-20.0F, -20.0F, 65.0F, -90.0F)
            .getEmotePose2(-95.0F, 15.0F, -45.0F, -85.0F)
            .getCollectionSupport2(10.0F, -22.0F, -10.0F, -8.0F, 28.0F, 8.0F));
    render(
        arrayList,
        44.25F,
        emotePose3.getEmotePose().getCollectionSupport2(20.0F, 0.0F, 0.0F, -18.0F, 0.0F, 0.0F));
    render(
        arrayList,
        45.5F,
        emotePose3.getEmotePose().getCollectionSupport2(20.0F, 0.0F, 0.0F, -18.0F, 0.0F, 0.0F));
    render(arrayList, 45.75F, emotePose5);
    render(
        arrayList,
        46.0F,
        getEmotePose(false, 1.3F)
            .getCollectionSupport2(-90.0F, -15.0F, 20.0F, -70.0F)
            .getEmotePose2(30.0F, 0.0F, -25.0F, -30.0F));
    render(arrayList, 46.5F, emotePose5.getEmotePose().getCollectionSupport2(-1.0F, 0.0F, 0.5F));
    render(
        arrayList,
        47.0F,
        getEmotePose(true, 1.2F)
            .getCollectionSupport2(30.0F, 0.0F, 25.0F, -30.0F)
            .getEmotePose2(-90.0F, 15.0F, -20.0F, -70.0F));
    render(arrayList, 47.5F, emotePose5.getEmotePose().getCollectionSupport2(1.0F, 0.0F, 0.5F));
    render(
        arrayList,
        48.0F,
        getEmotePose(false, 1.1F).getCollectionSupport2(-110.0F, -15.0F, 38.0F, -30.0F));
    render(
        arrayList, 48.5F, getEmotePose(true, 0.65F).getEmotePose2(-100.0F, 15.0F, -38.0F, -30.0F));
    render(
        arrayList,
        49.0F,
        getEmotePose(false, 0.75F).getCollectionSupport2(-60.0F, 0.0F, 65.0F, -85.0F));
    render(arrayList, 49.5F, getEmotePose(true, 0.75F).getEmotePose2(-60.0F, 0.0F, -65.0F, -85.0F));
    render(
        arrayList, 50.0F, getCollectionSupport2(false).getCollectionSupport2(-2.0F, -0.5F, 0.8F));
    render(arrayList, 50.75F, emotePose5.getEmotePose().getCollectionSupport2(-0.8F, 0.0F, 0.0F));
    render(arrayList, 51.0F, getCollectionSupport2(true).getCollectionSupport2(2.0F, -0.5F, 0.9F));
    render(arrayList, 51.5F, emotePose3.getEmotePose().getCollectionSupport2(0.8F, 0.0F, 0.4F));
    render(
        arrayList,
        52.0F,
        emotePose2
            .getEmotePose()
            .getCollectionSupport22(-52.0F, 12.0F, 68.0F, -12.0F, -10.0F, 18.0F));
    render(arrayList, 52.75F, emotePose5);
    render(
        arrayList,
        53.0F,
        emotePose3.getEmotePose().getCollectionSupport2(15.0F, 24.0F, 8.0F, -10.0F, -15.0F, -6.0F));
    render(
        arrayList,
        53.5F,
        emotePose4
            .getEmotePose()
            .getCollectionSupport22(-62.0F, 28.0F, 15.0F, -14.0F, -14.0F, 24.0F)
            .getCollectionSupport2(-1.7F, 0.0F, 0.5F));
    render(
        arrayList,
        54.0F,
        emotePose5
            .getEmotePose()
            .getCollectionSupport2(22.0F, -16.0F, -12.0F, -12.0F, 10.0F, 8.0F));
    render(
        arrayList,
        54.5F,
        emotePose2
            .getEmotePose()
            .getEmotePose2(-105.0F, 22.0F, -30.0F, -12.0F)
            .getCollectionSupport2(-20.0F, -5.0F, 42.0F, -80.0F));
    render(
        arrayList,
        55.0F,
        emotePose4
            .getEmotePose()
            .getCollectionSupport22(-14.0F, 14.0F, 24.0F, -60.0F, -28.0F, 15.0F)
            .getCollectionSupport2(1.7F, 0.0F, 0.5F));
    render(arrayList, 55.75F, emotePose5);
    render(arrayList, 56.0F, emotePose3.getEmotePose().getCollectionSupport2(0.0F, 0.0F, 0.6F));
    render(
        arrayList,
        56.5F,
        emotePose4
            .getEmotePose()
            .getCollectionSupport2(-145.0F, -15.0F, 48.0F, -20.0F)
            .getEmotePose2(-35.0F, 10.0F, -75.0F, -40.0F));
    render(
        arrayList,
        57.0F,
        getCollectionSupport2(true)
            .getCollectionSupport2(-10.0F, 22.0F, 12.0F, 6.0F, -16.0F, -8.0F));
    render(
        arrayList,
        57.5F,
        getCollectionSupport2(false)
            .getCollectionSupport2(-10.0F, -22.0F, -12.0F, 6.0F, 16.0F, 8.0F));
    render(arrayList, 58.0F, emotePose2.getEmotePose().getCollectionSupport2(-1.5F, 0.0F, 0.0F));
    render(arrayList, 59.0F, emotePose2.getEmotePose().getCollectionSupport2(-1.5F, 0.0F, 0.0F));
    render(arrayList, 59.5F, emotePose3);
    render(arrayList, 60.0F, emotePose4.getEmotePose().getCollectionSupport2(0.0F, 0.0F, 1.7F));
    render(
        arrayList,
        60.75F,
        emotePose5
            .getEmotePose()
            .getCollectionSupport2(20.0F, -25.0F, -5.0F, -10.0F, -25.0F, 5.0F));
    render(
        arrayList,
        61.0F,
        getEmotePose(false, 0.65F)
            .getCollectionSupport2(-45.0F, 0.0F, 45.0F, -95.0F)
            .getEmotePose2(-45.0F, 0.0F, -45.0F, -95.0F)
            .getCollectionSupport2(0.0F));
    render(
        arrayList,
        62.0F,
        getEmotePose(true, 0.65F)
            .getCollectionSupport2(-45.0F, 0.0F, 32.0F, -105.0F)
            .getEmotePose2(-45.0F, 0.0F, -32.0F, -105.0F)
            .getCollectionSupport2(85.0F));
    render(
        arrayList,
        63.0F,
        getEmotePose(false, 0.6F)
            .getCollectionSupport2(-40.0F, 0.0F, 28.0F, -110.0F)
            .getEmotePose2(-40.0F, 0.0F, -28.0F, -110.0F)
            .getCollectionSupport2(180.0F));
    render(
        arrayList,
        64.0F,
        getEmotePose(true, 0.65F)
            .getCollectionSupport2(-45.0F, 0.0F, 48.0F, -70.0F)
            .getEmotePose2(-45.0F, 0.0F, -48.0F, -70.0F)
            .getCollectionSupport2(280.0F));
    render(
        arrayList,
        65.0F,
        emotePose4
            .getEmotePose()
            .getCollectionSupport2(0.0F, 0.0F, 0.5F)
            .getCollectionSupport2(360.0F));
    render(arrayList, 65.5F, emotePose5.getEmotePose().getCollectionSupport2(360.0F));
    render(arrayList, 66.0F, getCollectionSupport2(false).getCollectionSupport2(360.0F));
    render(arrayList, 66.5F, getCollectionSupport2(true).getCollectionSupport2(360.0F));
    render(arrayList, 67.0F, emotePose3.getEmotePose().getCollectionSupport2(360.0F));
    render(arrayList, 67.75F, emotePose3.getEmotePose().getCollectionSupport2(360.0F));
    render(
        arrayList,
        68.0F,
        emotePose4
            .getEmotePose()
            .getCollectionSupport2(0.0F, 0.0F, 2.0F)
            .getCollectionSupport2(360.0F));
    render(arrayList, 68.75F, emotePose5.getEmotePose().getCollectionSupport2(360.0F));
    render(arrayList, 69.0F, emotePose3.getEmotePose().getCollectionSupport2(360.0F));
    render(arrayList, 69.5F, emotePose4.getEmotePose().getCollectionSupport2(360.0F));
    render(
        arrayList,
        70.0F,
        emotePose2
            .getEmotePose()
            .getEmotePose2(-25.0F, 0.0F, -70.0F, -80.0F)
            .getCollectionSupport2(360.0F));
    render(
        arrayList,
        71.25F,
        emotePose2
            .getEmotePose()
            .getEmotePose2(-25.0F, 0.0F, -70.0F, -80.0F)
            .getCollectionSupport2(360.0F));
    render(arrayList, 72.2F, emotePose.getEmotePose().getCollectionSupport2(360.0F));
    return (bunnythekenzo.client.CollectionData2[]) (arrayList.toArray(CollectionData2[]::new));
  }

  public static EmotePose getEmotePose(boolean flag, float floatValue) {
    float floatValue2 = flag ? 1.0F : -1.0F;
    return new EmotePose()
        .getCollectionSupport22(
            flag ? -12.0F : -58.0F * floatValue,
            12.0F,
            flag ? 24.0F : 68.0F * floatValue,
            flag ? -58.0F * floatValue : -12.0F,
            -12.0F,
            flag ? 68.0F * floatValue : 24.0F)
        .getCollectionSupport2(flag ? -65.0F : 18.0F, -10.0F, 28.0F, -55.0F)
        .getEmotePose2(flag ? 18.0F : -65.0F, 10.0F, -28.0F, -55.0F)
        .getCollectionSupport2(
            9.0F,
            -12.0F * floatValue2,
            8.0F * floatValue2,
            -6.0F,
            8.0F * floatValue2,
            -6.0F * floatValue2)
        .getCollectionSupport2(floatValue2 * 1.3F, 0.0F, 0.35F);
  }

  public static EmotePose getCollectionSupport2(boolean flag) {
    float floatValue = flag ? 1.0F : -1.0F;
    return new EmotePose()
        .getCollectionSupport2(
            flag ? -18.0F : -148.0F, -12.0F, flag ? 38.0F : 32.0F, flag ? -95.0F : -12.0F)
        .getEmotePose2(
            flag ? -148.0F : -18.0F, 12.0F, flag ? -32.0F : -38.0F, flag ? -12.0F : -95.0F)
        .getCollectionSupport22(-22.0F, 22.0F, 35.0F, -22.0F, -22.0F, 35.0F)
        .getCollectionSupport2(
            -6.0F,
            18.0F * floatValue,
            15.0F * floatValue,
            4.0F,
            -10.0F * floatValue,
            -10.0F * floatValue)
        .getCollectionSupport2(floatValue * 1.7F, 0.0F, 0.0F);
  }

  public static void render(List list, float floatValue, EmotePose emotePose) {
    float amount3 = computeFloat(floatValue);
    if (!list.isEmpty() && amount3 <= ((CollectionData2) list.getLast()).amount()) {
      throw new IllegalArgumentException(DiagnosticTokenGenerator.tokenFor(1101));
    }

    list.add(new CollectionData2(amount3, (float[]) emotePose.amountArray.clone()));
  }

  public static float computeFloat(float floatValue) {
    int index = 0;

    while (index < amountArray.length - 2 && floatValue > amountArray[index + 1][0]) {
      index++;
    }

    float[] floatValues = amountArray[index];
    float[] floatValues2 = amountArray[index + 1];
    return floatValues[1]
        + (floatValues2[1] - floatValues[1])
            * (floatValue - floatValues[0])
            / (floatValues2[0] - floatValues[0]);
  }

  public static void render(
      BipedEntityModel bipedEntityModel, float floatValue, float floatValue2) {
    if (Float.isFinite(floatValue) && Float.isFinite(floatValue2) && !(floatValue2 <= 0.0F)) {
      float floatValue3 = Math.clamp(floatValue2, 0.0F, 1.0F);
      float floatValue4 = Math.clamp(floatValue, 0.0F, 32.0F);
      int index = 0;
      int index2 = collectionData2Array.length - 1;

      while (index2 - index > 1) {
        int index3 = index2 + index >>> 1;
        if (collectionData2Array[index3].amount() <= floatValue4) {
          index = index3;
        } else {
          index2 = index3;
        }
      }

      CollectionData2 collectionData2 = collectionData2Array[index];
      CollectionData2 collectionData22 = collectionData2Array[index2];
      float floatValue5 =
          Math.clamp(
              (floatValue4 - collectionData2.amount())
                  / (collectionData22.amount() - collectionData2.amount()),
              0.0F,
              1.0F);
      floatValue5 = floatValue5 * floatValue5 * (3.0F - 2.0F * floatValue5);
      render(
          bipedEntityModel.rightArm,
          collectionData2,
          collectionData22,
          floatValue5,
          0,
          floatValue3);
      render(
          bipedEntityModel.leftArm, collectionData2, collectionData22, floatValue5, 3, floatValue3);
      render(bipedEntityModel.body, collectionData2, collectionData22, floatValue5, 6, floatValue3);
      render(bipedEntityModel.head, collectionData2, collectionData22, floatValue5, 9, floatValue3);
      render(
          bipedEntityModel.rightLeg,
          collectionData2,
          collectionData22,
          floatValue5,
          12,
          floatValue3);
      render(
          bipedEntityModel.leftLeg,
          collectionData2,
          collectionData22,
          floatValue5,
          15,
          floatValue3);
      float floatValue6 =
          computeFloat(collectionData2, collectionData22, floatValue5, 18)
              * (float) (Math.PI / 180.0)
              * floatValue3;
      float floatValue7 =
          computeFloat(collectionData2, collectionData22, floatValue5, 19)
              * (float) (Math.PI / 180.0)
              * floatValue3;
      float floatValue8 =
          computeFloat(collectionData2, collectionData22, floatValue5, 20)
              * (float) (Math.PI / 180.0)
              * floatValue3;
      float floatValue9 =
          computeFloat(collectionData2, collectionData22, floatValue5, 21)
              * (float) (Math.PI / 180.0)
              * floatValue3;
      EmoteBodyParts.render(bipedEntityModel, floatValue6, floatValue7, floatValue8, floatValue9);
      float floatValue10 =
          computeFloat(collectionData2, collectionData22, floatValue5, 6)
              * (float) (Math.PI / 180.0)
              * floatValue3;
      float floatValue11 =
          computeFloat(collectionData2, collectionData22, floatValue5, 7)
              * (float) (Math.PI / 180.0)
              * floatValue3;
      float floatValue12 =
          computeFloat(collectionData2, collectionData22, floatValue5, 8)
              * (float) (Math.PI / 180.0)
              * floatValue3;
      render(bipedEntityModel.body, floatValue10, floatValue11, floatValue12);
      render(bipedEntityModel.head, floatValue10, floatValue11, floatValue12);
      render(bipedEntityModel.rightArm, floatValue10, floatValue11, floatValue12);
      render(bipedEntityModel.leftArm, floatValue10, floatValue11, floatValue12);
      ModelPart rootPart = bipedEntityModel.getRootPart();
      rootPart.originX =
          rootPart.originX
              + computeFloat(collectionData2, collectionData22, floatValue5, 22) * floatValue3;
      rootPart = bipedEntityModel.getRootPart();
      rootPart.originZ =
          rootPart.originZ
              + computeFloat(collectionData2, collectionData22, floatValue5, 23) * floatValue3;
      float floatValue13 = computeFloat(collectionData2, collectionData22, floatValue5, 25);
      if (floatValue4 >= computeFloat(65.0F)) {
        floatValue13 -= 360.0F;
      }

      bipedEntityModel.getRootPart().yaw += floatValue13 * (float) (Math.PI / 180.0) * floatValue3;
      float floatValue14 =
          Math.max(
              computeFloat(bipedEntityModel.rightLeg, floatValue8),
              computeFloat(bipedEntityModel.leftLeg, floatValue9));
      rootPart = bipedEntityModel.getRootPart();
      rootPart.originY =
          rootPart.originY
              + (24.0F
                  - floatValue14
                  - computeFloat(collectionData2, collectionData22, floatValue5, 24) * floatValue3);
    }
  }

  public static void render(
      ModelPart modelPart,
      CollectionData2 collectionData2,
      CollectionData2 collectionData22,
      float floatValue,
      int intValue,
      float floatValue2) {
    modelPart.pitch =
        modelPart.pitch
            + (computeFloat(collectionData2, collectionData22, floatValue, intValue)
                        * (float) (Math.PI / 180.0)
                    - modelPart.pitch)
                * floatValue2;
    modelPart.yaw =
        modelPart.yaw
            + (computeFloat(collectionData2, collectionData22, floatValue, intValue + 1)
                        * (float) (Math.PI / 180.0)
                    - modelPart.yaw)
                * floatValue2;
    modelPart.roll =
        modelPart.roll
            + (computeFloat(collectionData2, collectionData22, floatValue, intValue + 2)
                        * (float) (Math.PI / 180.0)
                    - modelPart.roll)
                * floatValue2;
  }

  public static float computeFloat(
      CollectionData2 collectionData2,
      CollectionData2 collectionData22,
      float floatValue,
      int index) {
    return collectionData2.amountArray()[index]
        + (collectionData22.amountArray()[index] - collectionData2.amountArray()[index])
            * floatValue;
  }

  public static void render(
      ModelPart modelPart, float floatValue, float floatValue2, float floatValue3) {
    float originX = modelPart.originX;
    float floatValue4 = modelPart.originY - 12.0F;
    float originZ = modelPart.originZ;
    float floatValue5 =
        floatValue4 * computeFloat3(floatValue) - originZ * computeFloat2(floatValue);
    float floatValue6 =
        floatValue4 * computeFloat2(floatValue) + originZ * computeFloat3(floatValue);
    float floatValue7 =
        originX * computeFloat3(floatValue2) + floatValue6 * computeFloat2(floatValue2);
    modelPart.originX =
        floatValue7 * computeFloat3(floatValue3) - floatValue5 * computeFloat2(floatValue3);
    modelPart.originY =
        12.0F + floatValue7 * computeFloat2(floatValue3) + floatValue5 * computeFloat3(floatValue3);
    modelPart.originZ =
        -originX * computeFloat2(floatValue2) + floatValue6 * computeFloat3(floatValue2);
  }

  public static float computeFloat(ModelPart modelPart, float floatValue) {
    float floatValue2 = -Float.MAX_VALUE;

    for (int index = 0; index < 8; index++) {
      float floatValue3 = (index & 1) == 0 ? -2.0F : 2.0F;
      float floatValue4 = (index & 2) == 0 ? 0.0F : 6.0F;
      float floatValue5 = (index & 4) == 0 ? -2.0F : 2.0F;
      float floatValue6 =
          6.0F + floatValue4 * computeFloat3(floatValue) - floatValue5 * computeFloat2(floatValue);
      float floatValue7 =
          floatValue4 * computeFloat2(floatValue) + floatValue5 * computeFloat3(floatValue);
      float floatValue8 =
          floatValue6 * computeFloat3(modelPart.pitch)
              - floatValue7 * computeFloat2(modelPart.pitch);
      float floatValue9 =
          floatValue6 * computeFloat2(modelPart.pitch)
              + floatValue7 * computeFloat3(modelPart.pitch);
      float floatValue10 =
          floatValue3 * computeFloat3(modelPart.yaw) + floatValue9 * computeFloat2(modelPart.yaw);
      floatValue2 =
          Math.max(
              floatValue2,
              modelPart.originY
                  + floatValue10 * computeFloat2(modelPart.roll)
                  + floatValue8 * computeFloat3(modelPart.roll));
    }

    return floatValue2;
  }

  public static float computeFloat2(float floatValue) {
    return (float) Math.sin(floatValue);
  }

  public static float computeFloat3(float floatValue) {
    return (float) Math.cos(floatValue);
  }
}
