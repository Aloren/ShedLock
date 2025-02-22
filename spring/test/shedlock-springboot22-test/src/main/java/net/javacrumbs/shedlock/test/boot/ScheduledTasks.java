/**
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.javacrumbs.shedlock.test.boot;

import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class ScheduledTasks implements Parent {
    private final AtomicBoolean called = new AtomicBoolean(false);

    @Scheduled(fixedRate = 100)
    @SchedulerLock(name = "reportCurrentTime", lockAtMostFor = "${lock.at.most.for}")
    @Transactional
    public void reportCurrentTime() {
        LockAssert.assertLocked();
        called.set(true);
        System.out.println(new Date());
    }

    boolean wasCalled() {
        return called.get();
    }
}
