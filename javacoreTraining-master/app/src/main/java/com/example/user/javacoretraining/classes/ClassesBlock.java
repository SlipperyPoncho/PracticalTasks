package com.example.user.javacoretraining.classes;

import android.os.Build;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Набор заданий по работе с классами в java.
 * <p>
 * Задания подразумевают создание класса(ов), выполняющих задачу.
 * <p>
 * Проверка осуществляется ментором.
 */
public interface ClassesBlock {

    /*
      I

      Создать класс с двумя переменными. Добавить функцию вывода на экран
      и функцию изменения этих переменных. Добавить функцию, которая находит
      сумму значений этих переменных, и функцию, которая находит наибольшее
      значение из этих двух переменных.
     */
    class Task1<T> {
        private T var1;
        private T var2;

        public void print() {
            System.out.println("var1 = " + var1 + '\n' + "var2 = " + var2);
        }

        public void setVars(T var1, T var2) {
            this.var1 = var1;
            this.var2 = var2;
        }

        public Double sum() {
            if (var1 instanceof Number && var2 instanceof Number) {
                return ((Number) var1).doubleValue() + ((Number) var2).doubleValue();
            }
            else return null;
        }

        public T max() {
            if (var1 instanceof Number && var2 instanceof Number) {
                return ((Number) var1).doubleValue() > ((Number) var2).doubleValue()
                        ? var1 : var2;
            } else return null;
        }
    }
    /*
      II

      Создать класс, содержащий динамический массив и количество элементов в нем.
      Добавить конструктор, который выделяет память под заданное количество элементов.
      Добавить методы, позволяющие заполнять массив случайными числами,
      переставлять в данном массиве элементы в случайном порядке, находить количество
      различных элементов в массиве, выводить массив на экран.
     */
    class Task2 {
        private List<Integer> list;

        Task2() { this.list = new ArrayList<>(10); }

        private <T> void rand(int min, int maxIncl, int count) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                list = new Random()
                        .ints(min, maxIncl + 1)
                        .limit(count)
                        .boxed()
                        .collect(Collectors.toList());
            }
        }

        private void randRearrange() {
            Collections.shuffle(list, new Random());
        }

        private int countUniqueElements() {
            Set<Integer> set = new HashSet<>(list);
            return set.size();
        }

        private void printList() {
            System.out.print(list);
        }
    }
    /*
      III

      Описать класс, представляющий треугольник. Предусмотреть методы для создания объектов,
      вычисления площади, периметра и точки пересечения медиан.
      Описать свойства для получения состояния объекта.
     */
    class Task3 {
        private int a;
        private int b;
        private int c;
        private double h;

        Task3(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
            h =  2 * Math.sqrt(perimeter()
                    * (perimeter() - a)
                    * (perimeter() - b)
                    * (perimeter() - c)) / a;
        }

        private double area() {
            return a * h / 2;
        }

        private double perimeter() {
            return a + b + c;
        }

        private double median() {
            return Math.sqrt(2 * b * b + 2 * c * c - 2 * a * a) / 2;
        }

        public int getA() { return a; }
        public int getB() { return b; }
        public int getC() { return c; }
    }
    /*
      IV

      Составить описание класса для представления времени.
      Предусмотреть возможности установки времени и изменения его отдельных полей
      (час, минута, секунда) с проверкой допустимости вводимых значений.
      В случае недопустимых значений полей выбрасываются исключения.
      Создать методы изменения времени на заданное количество часов, минут и секунд.
     */
    class Task4 {
        private int hour;
        private int minute;
        private int second;

        Task4(int hour, int minute, int second) {
            this.hour = hour;
            this.minute = minute;
            this.second = second;
        }

        private String setTime(int h, int m, int s) throws Exception {
            if (h > -1 && h < 24 && m > -1 && m < 60 && s > -1 && s < 60) {
                hour = h;
                minute = m;
                second = s;
            } else throw new Exception("invalid time");
            return hour + ":" + minute + ":" + second;
        }

        private void changeHours(int n) {
            hour += n;
            if (hour > 23) hour = n - (24 - hour);
            if (hour < 0) hour = 24 - (n - hour);
        }
        private void changeMinutes(int n) {
            minute += n;
            if (minute > 59) minute = n - (60 - minute);
            if (minute < 0) minute = 60 - (n - minute);
        }
        private void changeSeconds(int n) {
            second += n;
            if (second > 59) second = n - (60 - second);
            if (second < 0) second = 60 - (n - second);
        }
    }
    /*
      V

      Класс Абонент: Идентификационный номер, Фамилия, Имя, Отчество, Адрес,
      Номер кредитной карточки, Дебет, Кредит, Время междугородных и городских переговоров;
      Конструктор; Методы: установка значений атрибутов, получение значений атрибутов,
      вывод информации. Создать массив объектов данного класса.
      Вывести сведения относительно абонентов, у которых время городских переговоров
      превышает заданное.  Сведения относительно абонентов, которые пользовались
      междугородной связью. Список абонентов в алфавитном порядке.
     */
    class Abonent {
        private int id;
        private String lastName;
        private String firstName;
        private String middleName; // Отчество
        private String address;
        private int creditCardId;
        private int debt;
        private int credit;
        private int timeCity; // Время городских переговоров
        private int timeInterCity; // Время междугородних переговоров

        public Abonent(int id,
                       String lastName,
                       String firstName,
                       String middleName,
                       String address,
                       int creditCardId,
                       int debt,
                       int credit,
                       int timeCity,
                       int timeInterCity) {
            this.id = id;
            this.lastName = lastName;
            this.firstName = firstName;
            this.middleName = middleName;
            this.address = address;
            this.creditCardId = creditCardId;
            this.debt = debt;
            this.credit = credit;
            this.timeCity = timeCity;
            this.timeInterCity = timeInterCity;
        }

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getMiddleName() { return middleName; }
        public void setMiddleName(String middleName) { this.middleName = middleName; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public int getCreditCardId() { return creditCardId; }
        public void setCreditCardId(int creditCardId) { this.creditCardId = creditCardId; }
        public int getDebt() { return debt; }
        public void setDebt(int debt) { this.debt = debt; }
        public int getCredit() { return credit; }
        public void setCredit(int credit) { this.credit = credit; }
        public int getTimeCity() { return timeCity; }
        public void setTimeCity(int timeCity) { this.timeCity = timeCity; }
        public int getTimeInterCity() { return timeInterCity; }
        public void setTimeInterCity(int timeInterCity) { this.timeInterCity = timeInterCity; }

        public void printAbonentInfo() {
            System.out.print("id = " + id + '\n'
                    + "Last Name = " + lastName + '\n'
                    + "First Name = " + firstName + '\n'
                    + "Middle Name = " + middleName + '\n'
                    + "Address = " + address + '\n'
                    + "Credit Card Id = " + creditCardId + '\n'
                    + "Debt = " + debt + '\n'
                    + "Credit = " + credit + '\n'
                    + "Time City = " + timeCity + '\n'
                    + "Time Inter City = " + timeInterCity + '\n'
            );
        }
    }

    class Task5 {
        private Abonent[] abonents = new Abonent[] {
                new Abonent(1, "Ivanov", "Ivan", "Ivanovich", "ooo", 12, 44, 15, 123, 0), // не пользовался междугородней
                new Abonent(2, "Petrov", "Petr", "Petrovich", "ooo", 65, 12, 20, 123, 123),
                new Abonent(3, "Ilya", "Ilya", "Ilya", "ooo", 51, 67, 15, 123, 0), // не пользовался междугородней
                new Abonent(4, "fed", "g", "f", "ooo", 34, 34, 15, 123, 323),
                new Abonent(5, "who", "am", "i", "ooo", 21, 55, 15, 123, 0), // не пользовался междугородней
        };

        private void printUsedInterCity() {
            for (int i = 0; i < abonents.length; i++) {
                if (abonents[i].timeInterCity > 0) {
                    abonents[i].printAbonentInfo();
                }
            }
        }

        //nonononononono
        private void printAlphabetAbonents() {
            Arrays.sort(abonents);
            for (Abonent a: abonents) {
                a.printAbonentInfo();
            }
        }
    }
    /*
      VI

      Задача на взаимодействие между классами. Разработать систему «Вступительные экзамены».
      Абитуриент регистрируется на Факультет, сдает Экзамены. Преподаватель выставляет Оценку.
      Система подсчитывает средний бал и определяет Абитуриента, зачисленного в учебное заведение.
     */
    class Enrollee {
        private Faculties faculty;

        public Enrollee(Faculties faculty) {
            this.faculty = faculty;
        }

        public void setFaculty(Faculties faculty) {
            this.faculty = faculty;
        }

        public Map<String, String> passExam(Map<String, String> examQuestions) throws IOException {
            Map<String, String> questionsAnswers = new HashMap<>();
            String enroleeAnswer = "";
            for (Map.Entry<String, String> question : examQuestions.entrySet()) {
                System.out.print(question.getKey());
                enroleeAnswer = String.valueOf(System.in.read());
                questionsAnswers.put(question.getKey(), enroleeAnswer);
            }
            return questionsAnswers;
        }
    }

    enum Faculties {
        PHYSICS,
        MATH,
        HISTORY
    }

    class Exam {
        private final Map<String, String> questions = new HashMap<>();

        public Map<String, String> getQuestions(Faculties faculty) {
            switch (faculty) {
                case PHYSICS:
                    questions.put(
                            "Кто получил первую Нобелевская премию по физике?",
                            "Вильгельм Рентген"
                            );
                    questions.put(
                            "Эта женщина в виде исключения дважды была лауреатом Нобелевской премии",
                            "Мария Склодовская–Кюри"
                    );
                    questions.put(
                            "Кто создал первый паровой двигатель в мире?",
                            "Иван Ползунов"
                    );
                    break;
                case MATH:
                    questions.put("2+2", "4");
                    questions.put("12+4", "16");
                    questions.put("11*8", "88");
                    break;
                case HISTORY:
                    questions.put("Первый чернокожий президент США", "Кендрик Ламар");
                    questions.put("Годы 2-ой мировой войны", "1941-1945");
                    questions.put("Дата основания Москвы", "1147");
                    break;
                default: break;
            }
            return questions;
        }
    }

    class Teacher {
        public static int estimate(String[] enroleeAnswers, String[] correctAnswers) {
            int count = 0;
            for (int i = 0; i < correctAnswers.length; i++) {
                if (Objects.equals(correctAnswers[i], enroleeAnswers[i])) {
                    count++;
                }
            }
            return count;
        }
    }

    class Task6 {
        private void registerEnrolee(Enrollee enrollee, Faculties faculty) {
            enrollee.setFaculty(faculty);
        }

        int examEnrollee(Enrollee enrollee, Exam exam, Faculties faculty) throws IOException {
            registerEnrolee(enrollee, faculty);
            Map<String, String> examQuestions = exam.getQuestions(enrollee.faculty);
            Map<String, String> enrolleeAnswers =  enrollee.passExam(examQuestions);

            return Teacher.estimate(enrolleeAnswers.values().toArray(new String[0]),
                    examQuestions.values().toArray(new String[0]));
        }

        boolean isPassed(Enrollee enrollee, Exam exam) throws IOException {
            int mathMark = examEnrollee(enrollee, exam, Faculties.MATH);
            int physicsMark = examEnrollee(enrollee, exam, Faculties.PHYSICS);
            int historyMark = examEnrollee(enrollee, exam, Faculties.HISTORY);

            double avg = (double)(mathMark + physicsMark + historyMark) / (double)3;

            return avg > 4.5;
        }
    }
    /*
      VII

      Задача на взаимодействие между классами. Разработать систему «Интернет-магазин».
      Товаровед добавляет информацию о Товаре. Клиент делает и оплачивает Заказ на Товары.
      Товаровед регистрирует Продажу и может занести неплательщика в «черный список».
     */
    class Product {
        private String title;
        private int price;

        public Product(String title, int price) {
            this.title = title;
            this.price = price;
        }

        public int getPrice() {return price;}
        public void setPrice(int price) {this.price = price;}
        public String getTitle() {return title;}
        public void setTitle(String title) {this.title = title;}
    }

    class Client {
        private final String name;
        private final int credit;
        private Product clientProduct;

        public Client(String name, int credit) {
            this.name = name;
            this.credit = credit;
        }

        public int getCredit() {return credit;}
        public String getName() {return name;}
        public Product getClientProduct() {return clientProduct;}

        public void makeOrder(Product product) {
            clientProduct.setTitle(product.getTitle());
            clientProduct.setPrice(product.getPrice());
        }
    }

    class Merchandiser {
        public HashMap<Client, Product> registerClientOrder(Client client) {
            HashMap<Client, Product> map = new HashMap<>();
            map.put(client, client.clientProduct);
            return map;
        }
    }

    class Task7 {
        List<Product> products = new ArrayList<> (Arrays.asList(
                new Product("Book", 12),
                new Product("Computer", 4000),
                new Product("Shampoo", 150))
        );
        List<Client> clients = new ArrayList<> (Arrays.asList(
                new Client("Ivan", 500),
                new Client("Petr", 5_000),
                new Client("Shaman", 200))
        );

        void setClientOrders() {
            for (int i = 0; i < clients.size(); i++) {
                clients.get(i).makeOrder(products.get(i));
            }
        }
    }
}
