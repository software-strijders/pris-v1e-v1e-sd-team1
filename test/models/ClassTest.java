package models;

import enums.SubjectType;
import models.user.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClassTest {

    private ArrayList<Student> students;
    private FieldOfStudy study;
    private Class newClass1;

    @BeforeEach
    void initialize() {
        Student student1 = new Student("xxx", "xxx", "xxx", "xxx");
        Student student2 = new Student("xxx", "xxx", "xxx", "xxx");
        Student student3 = new Student("xxx", "xxx", "xxx", "xxx");

        students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);

        study = new FieldOfStudy("TICT-SD");
        newClass1 = new Class(1, 'E', study, students);
    }

    @Test
    void shouldReturnUpcomingLectures() {
        Lecture l1 = new Lecture(LocalDateTime.now().plusMinutes(1), 0, null, null, null);
        Class c1 = new Class(0, (char)0, null);
        c1.addLecture(l1);
        assertTrue(c1.getUpcomingLectures().contains(l1));
    }

    @Test
    void shouldReturnPastLectures() {
        Lecture l1 = new Lecture(LocalDateTime.now().minusDays(1), 0, null, null, null);
        Class c1 = new Class(0, (char)0, null);
        c1.addLecture(l1);
        assertFalse(c1.getUpcomingLectures().contains(l1));
    }

    @Test
    void shouldReturnLecturesBySameSubjectAndSameDate() {
        Lecture l1 = new Lecture(LocalDateTime.now(), 0, SubjectType.OOP, null, null);
        Class c1 = new Class(0, (char)0, null);
        c1.addLecture(l1);
        assertTrue(c1.getLecturesBySubjectAndDate(SubjectType.OOP, LocalDate.now()).contains(l1));
    }

    @Test
    void shouldNotReturnLecturesByOtherSubjectAndSameDate() {
        Lecture l1 = new Lecture(LocalDateTime.now(), 0, SubjectType.GP_SD, null, null);
        Class c1 = new Class(0, (char)0, null);
        c1.addLecture(l1);
        assertFalse(c1.getLecturesBySubjectAndDate(SubjectType.OOP, LocalDate.now()).contains(l1));
    }

    @Test
    void shouldNotReturnLecturesBySameSubjectAndOtherDate() {
        Lecture l1 = new Lecture(LocalDateTime.now().plusDays(1), 0, SubjectType.OOP, null, null);
        Class c1 = new Class(0, (char)0, null);
        c1.addLecture(l1);
        assertFalse(c1.getLecturesBySubjectAndDate(SubjectType.OOP, LocalDate.now()).contains(l1));
    }

    @Test
    void classNameShouldBeFormattedCorrectly() {
        assertEquals("TICT-SD-V1E", newClass1.getName());
    }

    @Test
    void classesShouldBeEqual() {
        Class newClass2 = new Class(1, 'E', study, students);
        assertEquals(newClass1, newClass2);
    }

    @Test
    void classesShouldNotBeEqual() {
        Class newClass2 = new Class(1, 'A', study, students);
        assertNotEquals(newClass1, newClass2);
    }

    @Test
    void yearOfStudyShouldBeZero() {
        Class newClass2 = new Class(0, 'A', study, students);
        assertEquals(0, newClass2.getYearOfStudy());
    }

    @Test
    void yearOfStudyShouldBeOne() {
        Class newClass2 = new Class(1, 'A', study, students);
        assertEquals(1, newClass2.getYearOfStudy());
    }

    @Test
    void groupShouldBeEqual() {
        Class newClass2 = new Class(1, 'A', study, students);
        assertEquals('A', newClass2.getGroup());
    }

    @Test
    void studyFieldShouldBeNull() {
        Class newClass2 = new Class(1, 'A', null, students);
        assertNull(newClass2.getStudyField());
    }

    @Test
    void studyFieldShouldNotBeNull() {
        Class newClass2 = new Class(1, 'A', study, students);
        assertNotNull(newClass2.getStudyField());
    }

    @Test
    void shouldReturnLecturesByEqualDate() {
        LocalDate date = LocalDate.of(2020, 2, 2);
        Lecture lecture = new Lecture(date.atStartOfDay(), 0, null, null, null);
        Class newClass = new Class(0, (char)0, null);
        newClass.addLecture(lecture);
        assertSame(lecture, newClass.getLecturesByDate(date).get(0));
    }

    @Test
    void shouldReturnNoLecturesByUnequalDate() {
        LocalDate date = LocalDate.of(2020, 2, 2);
        Lecture lecture = new Lecture(date.atStartOfDay(), 0, null, null, null);
        Class newClass = new Class(0, (char)0, null);
        newClass.addLecture(lecture);
        assertTrue(newClass.getLecturesByDate(date.plusDays(1)).isEmpty());
    }
}
