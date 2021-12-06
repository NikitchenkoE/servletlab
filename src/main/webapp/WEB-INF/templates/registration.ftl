<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
  <title>Registration page</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Shop</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="/products">All products</a>
        </li>
      </ul>
    </div>
  </div>
</nav>

<div class="container text-center">

  <div>
    <h2 style="color: #6c757d">Registration</h2>
  </div>

  <form action="/registration" method="POST">
    <div class="m-3">
      <div class="input-group mb-3">
        <label class="col-4 col-form-label" style="color: #6c757d">Username</label>
        <div class="col-4">
          <input type="text" class="form-control" name="userName"/>
        </div>
      </div>

      <div class="input-group mb-3">
        <label class="col-4 col-form-label" style="color: #6c757d">Password</label>
        <div class="col-4">
          <input type="password" class="form-control" name="password"/>
        </div>
      </div>
      <button type="submit" class="btn btn-primary" style="margin-top: 1%">Join</button>
    </div>
  </form>

</div>
</body>
</html>