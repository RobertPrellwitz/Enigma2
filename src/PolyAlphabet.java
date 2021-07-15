public class PolyAlphabet {

    public PolyAlphabet(String sample)
    {
        //String message = sample;
        cipher(sample);
    }

    public PolyAlphabet(String Sample, int num1, int num2)
    {
        cipher(Sample,num1,num2);
    }

    public String cipher(String sample , int c1, int c2)
    {
        StringBuilder caesar = new StringBuilder(sample.length());
        swap(caesar,sample,c1,c2);
        return caesar.toString();
    }

    public String cipher(String message)
    {
        StringBuilder caesar = new StringBuilder(message.length());

        int c1 = 5; int c2 = 19; int shift; char temp; int j;int count = 0;

        swap(caesar,message,5,19);
//        for(int i = 0; i < message.length(); i++)
//        {
//
//            j = count/5;
//            if ( (count == j*5+1) || (count==j*5+2) || (count==j*5+4) )
//            {
//                shift = c2;
//            }
//            else { shift = c1; }
//
//            char C = message.charAt(i);
//            int test = C;
//            if (test > 64 && test < 91)
//            {   test = test + shift;
//                if (test > 90)
//                    test = test - 26;
//                temp = (char) (test);
//                count++;
//            }
//            else if (test > 96 && test < 123)
//            {
//                test = test + shift;
//                if (test > 122)
//                    test = test - 26;
//                temp = (char) (test);
//                count++;
//            }
//            else {
//                temp = (char) (test);
//            }
//            caesar.append(temp);
//        }

        return caesar.toString();

    }
    public void swap (StringBuilder caesar, String message, int num1, int num2)
    {
        int c1 = num1; int c2 = num2; int shift; char temp; int j;int count = 0;
        for(int i = 0; i < message.length(); i++)
        {

            j = count/5;
            if ( (count == j*5+1) || (count==j*5+2) || (count==j*5+4) )
            {
                shift = c2;
            }
            else { shift = c1; }

            char C = message.charAt(i);
            int test = C;
            if (test > 64 && test < 91)
            {   test = test + shift;
                if (test > 90)
                    test = test - 26;
                temp = (char) (test);
                count++;
            }
            else if (test > 96 && test < 123)
            {
                test = test + shift;
                if (test > 122)
                    test = test - 26;
                temp = (char) (test);
                count++;
            }
            else {
                temp = (char) (test);
            }
            caesar.append(temp);
        }
    }

}
