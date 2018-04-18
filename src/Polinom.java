import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polinom {

    private String str;

    private int[] coefficients;

    public Polinom(int[] arg) {
        coefficients = new int[arg.length];
        for (int i=0; i < arg.length; i++) {
            setCoefficient(i, arg[i]);
        }
    }

    public int getCoefficient(int power) {
        if(power > coefficients.length-1){
            return 0;
        }
        return coefficients[power];
    }

    public void setCoefficient(int power, int value) {
        coefficients[power] = value;
    }

    public String getStr() {
        return toString(this.coefficients);
    }

    public void setStr(int[] arg) {
        str = toString(arg);
    }

    public Polinom(String arg) {
        if (!arg.matches("([+-]?\\d*x\\^\\d+)*([+-]?\\d*x)?([+-]?\\d+)?"))
            throw new NumberFormatException("Неправильная форма полинома");
        else coefficients = transformation(arg);
    }

    static private int[] transformation(String str) {
        if (!str.contains("x")) {
            return new int[] {Integer.parseInt(str)};
        } else if (str.matches("x\\^\\d+[+-]?\\d*") || str.matches("-?\\d*x[+-]?\\d*")) {
            int a0 = 0;
            if (str.matches("x\\^\\d+[+-]?\\d*")) {
                String[] parts;
                if (str.contains("+") || str.contains("-")) {
                    parts = str.split("[+-]\\d+")[0].split("x\\^");
                    a0 = Integer.parseInt(str.split("x\\^\\d+")[1]);
                } else {
                    parts = str.split("x\\^");
                    a0 = 0;
                }
                int[] coefficients = new int[Integer.parseInt(parts[1]) + 1];
                coefficients[Integer.parseInt(parts[1])] = 1;
                coefficients[0] = a0;
                return coefficients;
            } else {
                if (str.matches("x"))
                    return new int[] {0, 1};
                else {
                    String[] parts = str.split("x");
                    int a1;
                    if (Objects.equals(parts[0], "")) a1=1;
                    else if (Objects.equals(parts[0], "-")) a1 = -1;
                    else a1 = Integer.parseInt(parts[0]);
                    if (parts.length == 2) {
                        a0 = Integer.parseInt(parts[1]);
                    } else a0 = 0;
                    return new int[] {a0, a1};
                }
            }
        } else {
            String[] parts = new String[]{str, ""};
            Pattern regex = Pattern.compile("[+-]\\d+$");
            Matcher m = regex.matcher(str);
            if (m.find()) {
                StringBuilder sb = new StringBuilder(str);
                parts[0] = sb.substring(0, m.start());
                parts[1] = sb.substring(m.start(), m.end());
            }
            String[] partsCoefficients = parts[0].split("x(\\^\\d+)?");
            String[] partsPower = parts[0].split("[+-]?\\d*x\\^?");
            int power;
            if (partsPower.length == 1) power = 1;
            else power = Integer.parseInt(partsPower[1]);
            int[] coefficients = new int[power + 1];
            for (int i = 0; i < partsCoefficients.length; i++) {
                if (i != partsCoefficients.length - 1)
                    power = Integer.parseInt(partsPower[i + 1]);
                if (i == partsCoefficients.length - 1) {
                    if (partsPower.length > partsCoefficients.length) {
                        power = Integer.parseInt(partsPower[partsPower.length - 1]);
                    } else {
                        power = 1;
                    }
                }
                if (Objects.equals(partsCoefficients[i], "")) coefficients[power] = 1;
                else if (Objects.equals(partsCoefficients[i], "+")) coefficients[power] = 1;
                else if (Objects.equals(partsCoefficients[i], "-")) coefficients[power] = -1;
                else coefficients[power] = Integer.parseInt(partsCoefficients[i]);
            }
            if (!Objects.equals(parts[1], "")) coefficients[0] = Integer.parseInt(parts[1]);
            return coefficients;
        }
    }

    public int valuePolinom(int value) {
        int result = 0;
        int count = 0;
        for (int element: this.coefficients) {
            result += element*Math.pow(value, count);
            count++;
        }
        return result;
    }

    public Polinom sum(Polinom str2) {
        int[] p1 = this.coefficients;
        int[] p2 = str2.coefficients;
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
            return new Polinom(result);
        }
        int powerMax = Math.max(p1.length, p2.length);
        int[] result = new int[powerMax];
        for (int i=0; i<powerMax; i++) {
            if (i<=p1.length-1) result[i]+=p1[i];
            if (i<=p2.length-1) result[i]+=p2[i];
        }
        return new Polinom(result);
    }

    public Polinom minus(Polinom str2) {
        int[] p1 = this.coefficients;
        int[] p2 = str2.coefficients;
        for (int i=0; i<p2.length; i++) {
            p2[i] = -p2[i];
        }
        return new Polinom(p1).sum(new Polinom(p2));
    }

    public Polinom multiply(Polinom str2) {
        int[] p1 = this.coefficients;
        int[] p2 = str2.coefficients;
        if ((p1.length == 1 && p1[0] == 0) || (p2.length == 1 && p2[0] == 0))
            return new Polinom("0");
        int newPower= p1.length - 1 + p2.length - 1;
        int [] pNew = new int[newPower+1];
        for (int i = 0; i < p1.length; i++) {
            for (int j = 0; j < p2.length; j++) {
                pNew[i + j] +=  p1[i] * p2[j];
            }
        }
        return new Polinom(pNew);
    }

    public Polinom divide(Polinom other) {
        int[] p1 = this.coefficients;
        int[] p2 = other.coefficients;
        if (p2.length == 1 && p2[0] == 0)
            throw new NumberFormatException("На 0 делить нельзя!");
        if (p1.length < p2.length) {
            return new Polinom(new int[] {0});
        } else {
            int[] result = new int[p1.length-p2.length+1];
            int power;
            int coefficient;
            int[] monomial;
            while (p1.length >= p2.length) {
                power = p1.length - p2.length;
                coefficient = p1[p1.length-1]/p2[p2.length-1];
                result[power]=coefficient;
                if (power != 0) {
                    monomial = new int[power+1];
                    monomial[power] = coefficient;
                } else monomial = new int[] {coefficient};
                p1 = new Polinom(p1).minus(new Polinom(p2).multiply(new Polinom(monomial))).coefficients;
                if (p1.length == 1 && p1[0] == 0) break;
            }
            return new Polinom(result);
        }
    }

    public Polinom remainder(Polinom str2) {
        return this.minus(str2.multiply(this.divide(str2)));
    }

    private String toString(int[] array) {
        StringBuilder result = new StringBuilder();
        for (int i=array.length-1; i>=0 ; i--) {
            if (i>1 && array[i] != 0) {
                if (array[i] == 1 || array[i] == -1) {
                    if (i == array.length-1 && array[i] == 1)
                        result.append("x^").append(i);
                    else if (array[i] == 1) result.append("+x^").append(i);
                    else result.append("-x^").append(i);
                } else {
                    if (i == array.length - 1 && array[i] > 1) {
                        result.append(array[i]).append("x^").append(i);
                    } else if (array[i] > 0)
                        result.append("+").append(array[i]).append("x^").append(i);
                    else result.append(array[i]).append("x^").append(i);
                }
            }
            if (i == 1 && array[i] != 0) {
                if (array[i] == 1 || array[i] == -1) {
                    if (i == array.length-1 && array[i] == 1)
                        result.append("x");
                    else if (array[i] == 1) result.append("+x");
                    else result.append("-x");
                } else {
                    if (i == array.length - 1 && array[i] > 1) {
                        result.append(array[i]).append("x");
                    } else if (array[i] > 0)
                        result.append("+").append(array[i]).append("x");
                    else result.append(array[i]).append("x");
                }
            }
            if (i==0) {
                if (i == array.length - 1 && array[i] == 0) {
                    result.append("0");
                } else {
                    if (i == array.length - 1 || array[i] < 0) {
                        result.append(array[i]);
                    } else if (array[i] != 0) result.append("+").append(array[i]);
                }
            }
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Polinom) {
            Polinom other = (Polinom) obj;
            int[] array1 = this.coefficients;
            int[] array2 = other.coefficients;
            if (array1.length == array2.length) {
                for (int i = 0; i < array1.length; i++)
                    if (array1[i] != array2[i]) return false;
            } else return false;
            return true;
        }
        return false;
    }
}