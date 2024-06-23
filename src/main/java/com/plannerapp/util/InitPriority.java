package com.plannerapp.util;

import com.plannerapp.model.entity.Priority;
import com.plannerapp.model.entity.PriorityType;
import com.plannerapp.repo.PriorityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InitPriority implements CommandLineRunner {
    private final Map<PriorityType, String> priorityDescription = Map.of(
            PriorityType.URGENT, "An urgent problem that blocks the system use until the issue is resolved.",
            PriorityType.IMPORTANT, "A core functionality that your product is explicitly supposed to perform is compromised.",
            PriorityType.LOW, "Should be fixed if time permits but can be postponed"
    );
    private final PriorityRepository priorityRepository;

    public InitPriority(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        long count = priorityRepository.count();
        if (count == 0){
            for (PriorityType priorityType : priorityDescription.keySet()){
                Priority priority = new Priority(priorityType, priorityDescription.get(priorityType));

                priorityRepository.save(priority);

            }
        }

    }
}
