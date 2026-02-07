package ao.okayulatech.erp.facturacao.repository.submission;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ao.okayulatech.erp.facturacao.model.submission.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, UUID> {
	Optional<Submission> findBySubmissionUUID(String submissionUUID);

	boolean existsBySubmissionUUID(String submissionUUID);
}
