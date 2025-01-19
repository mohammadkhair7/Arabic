public static void main(String[] args) {
    if (args.length < 4) {
        System.out.println("Usage: java ADATMain <input_file> <output_file> <input_encoding> <output_encoding>");
        return;
    }

    try {
        // Get file paths from arguments
        String inputFile = args[0];
        String outputFile = args[1];
        String inputEncoding = args[2];
        String outputEncoding = args[3];

        // Read input file
        File input = new File(inputFile);
        if (!input.exists() || !input.canRead()) {
            System.out.println("Input File Error ...!!");
            return;
        }

        // Check output file
        File output = new File(outputFile);
        if (output.exists() && !output.canWrite()) {
            System.out.println("Output File Error ...!!");
            return;
        }

        // ... rest of the code
    } catch (Exception e) {
        e.printStackTrace();
    }
} 