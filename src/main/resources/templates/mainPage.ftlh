<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Main Page</title>
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
                <li class="nav-item">
                    <a class="nav-link" href="/products/add">Add Product</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container text-center" size="1000px">
    <h3 style="color: #6c757d" class="align-self-center">Table products</h3>


    <table class="table table-bordered">
        <thead>
        <tr>
            <th scope="col">Product id</th>
            <th scope="col">Product name</th>
            <th scope="col">Price</th>
            <th scope="col">Created</th>
            <th scope="col">Last time updated</th>
            <th scope="col" colspan="2">Action</th>
        </tr>
        </thead>
        <tbody>

        <#list products as product>
            <tr>
                <th scope="row">${product.id}</th>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.create}</td>
                <td>${product.update}</td>
                <td>
                    <form action="/products/delete?idToDelete=${product.id}" method="post">
                        <button class="btn btn-outline-danger" type="submit" value="Delete Product">Delete</button>
                    </form>
                </td>
                <td>
                    <button class="btn btn-outline-success"
                            onclick="location.href='http://localhost:8080/products/update?idToUpdate=${product.id}'"
                            type="button">Update
                    </button>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>

</body>
</html>