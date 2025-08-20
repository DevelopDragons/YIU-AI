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
export const UserEntranceLabel: Record<UserEntrance, string> = {
  [UserEntrance.FRESH]: "신입",
  [UserEntrance.TRANSFER]: "편입",
};

// [User(STUDENT) - 학생 재적 상태(UserStatus)
export enum UserStatus {
  STUDENT = "STUDENT",
  GRADUATE = "GRADUATE",
  COMPLETION = "COMPLETION",
  DISMISSAL = "DISMISSAL",
  ABSENCE = "ABSENCE",
}
export const UserStatusLabel: Record<UserStatus, string> = {
  [UserStatus.STUDENT]: "재학",
  [UserStatus.GRADUATE]: "졸업",
  [UserStatus.COMPLETION]: "수료",
  [UserStatus.DISMISSAL]: "제적",
  [UserStatus.ABSENCE]: "휴학",
};

// [User(STUDENT) - 전공 구분(UserTrack)
export enum UserTrack {
  SINGLE = "SINGLE",
  DOUBLE = "DOUBLE",
  MINOR = "MINOR",
  INTERDEPARTMENTAL = "INTERDEPARTMENTAL",
}
export const UserTrackLabel: Record<UserTrack, string> = {
  [UserTrack.SINGLE]: "단일전공",
  [UserTrack.DOUBLE]: "복수전공",
  [UserTrack.MINOR]: "부전공",
  [UserTrack.INTERDEPARTMENTAL]: "연계전공",
};

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

// [UserDepartment - 학과/전공 구분]
export enum UserDepartment {
  COMPUTER_SCIENCE = "COMPUTER_SCIENCE",
  LOGISTICS_STATISTICS = "LOGISTICS_STATISTICS",
  AI_MAJOR = "AI_MAJOR",
  BIGDATA_MAJOR = "BIGDATA_MAJOR",
  AI_UNDERGRAD = "AI_UNDERGRAD",
  AI_CONVERGENCE = "AI_CONVERGENCE",
}
export const UserDepartmentLabel: Record<UserDepartment, string> = {
  [UserDepartment.COMPUTER_SCIENCE]: "컴퓨터과학",
  [UserDepartment.LOGISTICS_STATISTICS]: "물류통계정보학",
  [UserDepartment.AI_MAJOR]: "AI전공",
  [UserDepartment.BIGDATA_MAJOR]: "빅데이터전공",
  [UserDepartment.AI_UNDERGRAD]: "AI학부",
  [UserDepartment.AI_CONVERGENCE]: "AI융합학부",
};
