package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstactUserServiceTest;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstactUserServiceTest {
}
