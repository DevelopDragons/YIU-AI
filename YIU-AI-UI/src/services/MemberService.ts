export {};
// const setMemberFormData = (data: any, files: any[]) => {
//   const formData = new FormData();
//   formData.append("majorGroupCode", "78");
//   formData.append("id", data.id);
//   formData.append("role", data.role);
//   formData.append("name", data.name);
//   formData.append("content1", data.content1);
//   formData.append("content2", data.content2);
//   formData.append("content3", data.content3);
//   formData.append("tel", data.tel);
//   formData.append("address", data.address);
//   formData.append("email", data.email);

//   // 새로 추가된 파일 추가
//   if (files.length > 0)
//     files.forEach((file) => {
//       formData.append("files", file.originFileObj); // 새로 추가한 파일 추가
//     });
//   else formData.append("files", new Blob()); // 빈 Blob 객체를 파일로 추가

//   return formData;
// };

// // 교수 등록
// export const reqCreateProfessor = async (data: any, files: any[]) => {
//   const formData = setMemberFormData(data, files);
//   const response = await authFileAPI.post(
//     "/manager/major/professor",
//     formData,
//     {
//       transformRequest: [
//         function () {
//           return formData;
//         },
//       ],
//     }
//   );

//   return response.data;
// };

// // 교수 수정
// export const reqUpdateProfessor = async (data: any, files: any[]) => {
//   const formData = setMemberFormData(data, files);

//   const response = await authFileAPI.put(
//     `/manager/major/professor?id=${data.id}`,
//     formData,
//     {
//       transformRequest: [
//         function () {
//           return formData;
//         },
//       ],
//     }
//   );

//   return response.data;
// };

// // 교수 삭제
// export const reqDeleteProfessor = async (id: any) => {
//   const response = await authDeleteAPI(`/manager/major/professor?id=${id}`);
//   return response.data;
// };

// // 학생회 등록
// export const reqCreateStudentCouncil = async (data: any, files: any[]) => {
//   const formData = setMemberFormData(data, files);
//   const response = await authFileAPI.post("/manager/major/council", formData, {
//     transformRequest: [
//       function () {
//         return formData;
//       },
//     ],
//   });

//   return response.data;
// };

// // 학생회 수정
// export const reqUpdateStudentCouncil = async (data: any, files: any[]) => {
//   const formData = setMemberFormData(data, files);

//   const response = await authFileAPI.put(
//     `/manager/major/council?id=${data.id}`,
//     formData,
//     {
//       transformRequest: [
//         function () {
//           return formData;
//         },
//       ],
//     }
//   );

//   return response.data;
// };

// // 학생회 삭제
// export const reqDeleteStudentCouncil = async (id: any) => {
//   const response = await authDeleteAPI(`/manager/major/council?id=${id}`);
//   return response.data;
// };
