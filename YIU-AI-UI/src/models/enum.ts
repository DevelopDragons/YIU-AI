// [User] - 유저 권한(UserRole)
export enum UserRole {
  SUPER = "SUPER",
  ADMIN = "ADMIN",
  STUDENT = "STUDENT",
}

// [User(STUDENT)] - 입학 구분(UserEntrance)
export enum UserEntrance {
  FRESH = "FRESH",
  TRANSFER = "TRANSFER",
}

// [User(STUDENT) - 학생 재적 상태(UserStatus)
export enum UserStatus {
  STUDENT = "STUDENT",
  GRADUATE = "GRADUATE",
  COMPLETION = "COMPLETION",
  DISMISSAL = "DISMISSAL",
  ABSENCE = "ABSENCE",
}

// [User(STUDENT) - 전공 구분(UserTrack)
export enum UserTrack {
  SINGLE = "SINGLE",
  DOUBLE = "DOUBLE",
  MINOR = "MINOR",
  INTERDEPARTMENTAL = "INTERDEPARTMENTAL",
}

// [Member] - 역할 구분(MemberRole)
export enum MemberRole {
  PROFESSOR = "PROFESSOR",
  ASSISTANT = "ASSISTANT",
}

// [Member(PROFESSOR)] - 교수 구분(ProfessorType)
export enum ProfessorType {
  FULLTIME = "FULLTIME",
  ADJUNCT = "ADJUNCT",
  INVITED = "INVITED",
  VISITING = "VISITING",
  RETIRED = "RETIRED",
}

// [Curriculum] - 주간이수단위(ClassCategory)
export enum ClassCategory {
  Theory = "Theory",
  Practice = "Practice",
}

// [Curriculum] - 전공구분(CourseCategory)
export enum CourseCategory {
  Basic = "Basic",
  Pro = "Pro",
}

// [MicroDegree] - 융합/MD 전공 구분(MicroDegreeCategory)
export enum MicroDegreeCategory {
  MD = "MD",
  Convergence = "CONVERGENCE",
}

// [MicroDegree] - 필수 여부(RequiredCategory)
export enum RequiredCategory {
  Required = "Required",
  Select = "Select",
}
