<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Cart</title>

    <style>
        table tr td:nth-child(3) {
            word-break: break-all;
        }

        .loginAndLogoutPosition {
            position: absolute;
            right: 3%;
            padding-top: 0.25%;
        }
    </style>
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
                <div class="loginAndLogoutPosition">
                    <li class="nav-item">
                        <a class="btn btn-outline-danger my-2 my-sm-0 btn-sm" href="/logout">logout</a>
                    </li>
                </div>
            </ul>
        </div>
    </div>
</nav>
<div class="container text-center" style="margin-top: 5%">
    <h3 style="color: #6c757d; margin-top: 3%; margin-bottom: 3%" class="align-self-center">Cart</h3>

    <table class="table table-bordered">
        <tr style="text-align:center; vertical-align: middle">
            <th scope="col">Product name</th>
            <th scope="col">Price</th>
            <th scope="col">Action</th>

        </tr>


        <#list products as product>
            <tr style="text-align:center; vertical-align: middle">
                <td>${product.productName}</td>
                <td>${product.productPrice}</td>
                <td>
                    <form action="/cart/deleteFromCart?idToDelete=${product.cartId}" method="post">
                        <button class="btn btn-outline-danger" type="submit" value="Delete Product">Delete</button>
                    </form>
                </td>
            </tr>
        </#list>
    </table>
</div>

<p>Total: ${total}$ </p>


</body>
</html>