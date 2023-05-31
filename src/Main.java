import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    /**
     * My processor has four cores
     */

     private static  String srcFolder = "/users/sortedmap/Desktop/src";
     private static  String dstFolder = "/users/sortedmap/Desktop/dst";

    public static void main(String[] args) {

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();

        int middle = files.length / 2;
        int quarter1 = middle / 2;
        int quarter2 = quarter1;
        if (quarter1 % 2 != 0) {
           quarter2++;
        }
        int threeQuarter = files.length - quarter2;

        File[] filesByThread1 = new File[quarter1];
        ImageResizer imageResizer1
                = getThread(files, 0, filesByThread1, 0, quarter1);
        new Thread(imageResizer1).start();
        System.out.println("Thread 1:  - " + imageResizer1.getFinish());

        File[] filesByThread2 = new File[quarter2];
        ImageResizer imageResizer2
                = getThread(files, quarter1, filesByThread2, 0, quarter2);
        new Thread(imageResizer2).start();
        System.out.println("Thread 2:  - " + imageResizer2.getFinish());

        File[] filesByThread3 = new File[quarter1];
        ImageResizer imageResizer3
                = getThread(files, middle, filesByThread3, 0, quarter1);
        new Thread(imageResizer3).start();
        System.out.println("Thread 3:  - " + imageResizer3.getFinish());

        File[] filesByThread4 = new File[quarter2];
        ImageResizer imageResizer4
                = getThread(files, threeQuarter, filesByThread4, 0, quarter2);
        new Thread(imageResizer4).start();
        System.out.println("Thread 4:  - " + imageResizer4.getFinish());

    }

    private static ImageResizer
                    getThread(File[] srcFiles, int scrPos, File[] dstFiles, int dstPos, int dstLength)
    {
        File [] resultFiles = dstFiles;
        System.arraycopy(srcFiles, scrPos, resultFiles, dstPos, dstLength);
        ImageResizer imageResizer = new ImageResizer(resultFiles, dstFolder);
        return imageResizer;
    }
}
