import { http, HttpResponse } from "msw";

export const handlers = [
  http.get("/posts", () => {
    console.log('Captured a "GET /posts" request');
  }),
  http.post("/posts", () => {
    console.log('Captured a "POST /posts" request');
  }),
  http.delete("/posts/:id", ({ params }) => {
    console.log(`Captured a "DELETE /posts/${params.id}" request`);
  }),
  http.get("/api/users", () => {
    return HttpResponse.json([
      {
        id: 1,
        nickName: "HojinHam",
        profileImg: "/fake-img.png",
      },
    ]);
  }),
];
