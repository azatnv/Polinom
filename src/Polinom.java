public class Polinom {

    private String str;

    public Polinom(String arg) {
        if (!arg.matches("([+-]?\\d*x\\^\\d+)*([+-]?\\d*x)?([+-]?\\d+)?")) throw new NumberFormatException("Неправильная форма полинома");
        else str=arg;
    }

    public int[] getPolinom() {
        return transformation(str);
    }

    static private int[] transformation(String str) {
        if (!str.contains("x")) {
            return new int[] {Integer.parseInt(str)};
        } else {
            String[] parts = str.split("(?=[+-]?\\d+$)");
            String[] partsCoefficients = parts[0].split("x(\\^\\d+)?");
            String[] partsPower = parts[0].split("[+-]?\\d*x\\^?");
            int[]  coefficients = new int[Integer.parseInt(partsPower[0])+1];
            for (int i=0; i<partsPower.length; i++) {
                int power;
                if (partsPower[i] == "") power = 1;
                else power = Integer.parseInt(partsPower[i]);
                if (partsCoefficients[i] == "") coefficients[power] = 1;
                else if (partsCoefficients[i] == "+") coefficients[power] = 1;
                else if (partsCoefficients[i] == "-") coefficients[power] = -1;
                else coefficients[power] = Integer.parseInt(partsCoefficients[i]);
            }
            if (parts.length == 2) coefficients[0] = Integer.parseInt(parts[1]);
            return coefficients;
        }
    }

    public int valuePolinom(Polinom p1, int value) {
        int result = 0;
        int count = 0;
        int[] coefficients = transformation(p1.str);
        for (int element: coefficients) {
            result += element*value^count;
            count++;
        }
        return result;
    }

    public Polinom sum(Polinom str1, Polinom str2) {
        int[] p1 = transformation(str1.str);
        int[] p2 = transformation(str2.str);
        int powerMin = Math.min(p1.length-1, p2.length-1);
        if (p1.length == p2.length) {
            int newPower = 0;
            for (int i=p1.length-1; i >= 0 ; i--) {
                if (p1[i] != -p2[i]) {
                    newPower = i;
                    break;
                }
            }
            int[] result = new int[newPower + 1];
            for (int i = 0; i <= newPower; i++) {
                result[i] = p1[i] + p2[i];
            }
            return new Polinom(toString(result));
        }
        if (p1.length>p2.length) {
            for (int i=0; i<=powerMin; i++) {
                p1[i] += p2[i];
            }
            return new Polinom(toString(p1));
        }
        else {
            for (int i=0; i<=powerMin; i++) {
                p2[i] += p1[i];
            }
            return new Polinom(toString(p1));
        }
    }

    public Polinom minus(Polinom str1, Polinom str2) {
        int[] p1 = transformation(str1.str);
        int[] p2 = transformation(str2.str);
        int powerMin = Math.min(p1.length-1, p2.length-1);
        if (p1.length == p2.length) {
            int newPower = 0;
            for (int i=p1.length-1; i >= 0 ; i--) {
                if (p1[i] != p2[i]) {
                    newPower = i;
                    break;
                }
            }
            int[] result = new int[newPower + 1];
            for (int i = 0; i <= newPower; i++) {
                result[i] = p1[i] - p2[i];
            }
            return new Polinom(toString(result));
        }
        if (p1.length > p2.length) {
            for (int i=0; i<=powerMin; i++) {
                p1[i] -= p2[i];
            }
            return new Polinom(toString(p1));
        }
        else {
            for (int g=0; g<p2.length; g++) p2[g]=-p2[g];
            for (int i=0; i<=powerMin; i++) {
                p2[i] += p1[i];
            }
            return new Polinom(toString(p2));
        }
    }

    public Polinom multiply(Polinom str1, Polinom str2) {
        int[] p1 = transformation(str1.str);
        int[] p2 = transformation(str2.str);
        int newPower= p1.length - 1 + p2.length - 1;
        int [] pNew = new int[newPower+1];
        for (int i = 0; i < p1.length; i++) {
            for (int j = 0; j < p2.length; j++) {
                pNew[i + j] +=  p1[i] * p2[j];
            }
        }
        return new Polinom(toString(pNew));
    }

    public Polinom divide(Polinom str1, Polinom str2) {
        int[] p1 = transformation(str1.str);
        int[] p2 = transformation(str2.str);
        if (p1.length < p2.length) {
            return new Polinom(toString(new int[] {0}));
        } else {
            int[] result = new int[p1.length-p2.length+1];
            int power;
            int coefficient;
            String monomial = "";
            while (p1.length>=p2.length) {
                power = p1.length - p2.length;
                int count = power;
                coefficient = p1[p1.length-1]/p2[p2.length-1];
                result[power]=coefficient;
                if (count > 1) monomial = coefficient + "x^" + power;
                else if (count == 1) monomial = coefficient + "x";
                else if (count == 0) monomial = coefficient + "";
                p1 = transformation(minus(new Polinom(toString(p1)), multiply(new Polinom(toString(p2)), new Polinom(monomial))).str);
            }
            return new Polinom(toString(result));
        }
    }

    private String toString(int[] array) {
        String result = "";
        for (int i=array.length-1; i>=0 ; i--) {
            if (i>1 && array[i] != 0) {
                if (array[i] == 1 || array[i] == -1) {
                    if (i == array.length-1 && array[i] == 1)
                        result+="x^"+i;
                    else if (array[i] == 1) result+="+x^"+i;
                    else result+="-x^"+i;
                } else {
                    if (i == array.length - 1 && array[i] > 1) {
                        result += array[i] + "x^" + i;
                    } else if (array[i] > 0)
                        result += "+" + array[i] + "x^" + i;
                    else result += array[i] + "x^" + i;
                }
            }
            if (i == 1 && array[i] != 0) {
                if (array[i] == 1 || array[i] == -1) {
                    if (i == array.length-1 && array[i] == 1)
                        result+="x";
                    else if (array[i] == 1) result+="+x";
                    else result+="-x";
                } else {
                    if (i == array.length - 1 && array[i] > 1) {
                        result += array[i] + "x";
                    } else if (array[i] > 0)
                        result += "+" + array[i] + "x";
                    else result += array[i] + "x";
                }
            }
            if (i==0) {
                if (i == array.length - 1 && array[i] == 0) {
                    result+="0";
                } else {
                    if (i == array.length - 1 || array[i] < 0) {
                        result += array[i];
                    } else result += "+" + array[i];
                }
            }
        }
        return result;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Polinom) {
            Polinom other = (Polinom) obj;
            int[] array1 = transformation(str);
            int[] array2 = transformation(other.str);
            boolean flag = true;
            if (array1.length == array2.length) {
                for (int i = 0; i < array1.length; i++)
                    if (array1[i] != array2[i]) flag = false;
            } else return false;
            return flag;
        }
        return false;
    }
}