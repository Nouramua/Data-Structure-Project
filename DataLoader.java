public class DataLoader {
    public static void Load(String fileName) {
        String line = null;
        try {
            File f = new File(fileName);
            Scanner s = new Scanner(f);
            s.nextLine(); 
            while (s.hasNextLine()) {
                line = s.nextLine();

                if (line.trim().length() < 3) {
                    break;
                }
                System.out.println(line);
                String x = line.substring(0, line.indexOf(","));
                int id = Integer.parseInt(x.trim());
                String content = line.substring(line.indexOf(",") + 1).trim();
            }
            s.close();
        } catch (Exception e) {
            System.out.println("end of file reached");
        }
    }
}
