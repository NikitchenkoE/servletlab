<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Main Page</title>

    <style>
        table tr td:nth-child(3) {
            word-break: break-all;
        }

        .searchInputsPositions {
            position: absolute;
            right: 3%;
            width: 20%;
            margin-top: 2%;
            margin-bottom: 2%;
        }

        .loginAndLogoutPosition {
            position: absolute;
            right: 3%;
            padding-top: 0.25%;
        }
    </style>
</head>
<body>
<p hidden>${logged}</p>
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
                    <a class="nav-link active" href="/products/add">Add Product</a>
                </li>
                <#if logged == "true">
                    <li class="nav-item">
                        <a class="nav-link active" href="/cart">My cart</a>
                    </li>
                </#if>
                <div class="loginAndLogoutPosition">
                    <#if logged == "false">
                        <li class="nav-item">
                            <a class="btn btn-outline-success btn-sm" href="/login">login</a>
                        </li>
                    </#if>
                    <#if logged == "true">
                        <li class="nav-item">
                            <a class="btn btn-outline-danger my-2 my-sm-0 btn-sm" href="/logout">logout</a>
                        </li>
                    </#if>
                </div>
            </ul>
        </div>
    </div>
</nav>

<div class="searchInputsPositions">
    <div class="input-group mb-3">
        <form class="d-flex" action="/productsById">
            <input class="form-control me-2" type="number" min="1" placeholder="Search by Id" aria-label="Search"
                   name="productId">
            <button class="btn btn-outline-success" type="submit">Search</button>
        </form>
    </div>
    <div class="input-group mb-3">
        <form class="d-flex" action="/productsByDescription">
            <input class="form-control me-2" type="text" placeholder="Search by description" aria-label="Search"
                   name="productDescription">
            <button class="btn btn-outline-success" type="submit">Search</button>
        </form>
    </div>
</div>
</div>
<div class="container text-center" style="margin-top: 5%">
    <h3 style="color: #6c757d; margin-top: 3%; margin-bottom: 3%" class="align-self-center">Table products</h3>

    <table class="table table-bordered">
        <tr style="text-align:center; vertical-align: middle">
            <th scope="col">Product id</th>
            <th scope="col">Product name</th>
            <th scope="col">Product description</th>
            <th scope="col">Price</th>
            <th scope="col">Created</th>
            <th scope="col">Last time updated</th>
            <#if logged == "true">
                <th scope="col" colspan="3">Action</th>
            </#if>
        </tr>


        <#list products as product>
            <tr style="text-align:center; vertical-align: middle">
                <th scope="row">${product.id}</th>
                <td>${product.name}</td>
                <td>${product.description}</td>
                <td>${product.price}</td>
                <td>${product.create}</td>
                <td>${product.update}</td>
                <#if logged == "true">
                    <td>
                        <form action="/cart?productId=${product.id}" method="post">
                            <button class="btn btn-outline-primary" type="submit" value="Add Product to cart">Add to cart</button>
                        </form>
                    </td>
                    <td>
                        <a class="btn btn-outline-success" href='/products/update?idToUpdate=${product.id}'>Update
                        </a>
                    </td>
                    <td>
                        <form action="/products/delete?idToDelete=${product.id}" method="post">
                            <button class="btn btn-outline-danger" type="submit" value="Delete Product">Delete</button>
                        </form>
                    </td>
                </#if>
            </tr>
        </#list>
    </table>
</div>
</body>
</html>