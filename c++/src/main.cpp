#include <chrono>
#include <cmath>
#include <cstdlib>
#include <ctime>
#include <iostream>
#include <thread>
#include <vector>

typedef unsigned short int usint;

namespace {

static const std::vector<int> bornAmounts = {3};
static const std::vector<int> surviveAmounts = {2, 3};
static const float startDistribution{0.2f};
static const float centerSize{1.f};

inline bool isInVector(const int targetValue, const std::vector<int>& values) {
    for (const auto& value : values) {
        if (targetValue == value) {
            return true;
        }
    }
    return false;
}

inline bool doBorn(const int neighboursAmount) {
    return isInVector(neighboursAmount, bornAmounts);
}

inline bool doSurvive(const int neighboursAmount) {
    return isInVector(neighboursAmount, surviveAmounts);
}

void clear_screen() {
    std::cout << std::string(100, '\n');
}

void fill_field(int y, int x, usint* field) {
    for (int i = 0; i < y * x; ++i) {
        const auto xCoord{i % x};
        const auto yCoord{i / x};
        const auto xDistanceToCenter{std::fabs(static_cast<float>(xCoord - x / 2)) / static_cast<float>(x / 2)};
        const auto yDistanceToCenter{std::fabs(static_cast<float>(yCoord - y / 2)) / static_cast<float>(y / 2)};
        if (xDistanceToCenter < centerSize && yDistanceToCenter < centerSize) {
            field[i] = static_cast<float>(rand() % 10000) / 10000 < startDistribution;
        } else {
            field[i] = 0;
        }
    }
}

void print_field(int y, int x, usint* field) {
    for (int j = 0; j < y; ++j) {
        for (int i = 0; i < x; ++i) {
            std::cout << (field[j * x + i] ? "\u2588\u2588" : "  ");
        }
        std::cout << '\n';
    }
}

int neighbours(int y, int x, usint* field, int index) {
    int amount{0};
    int i{index % x};
    int j{index / x};
    if (j == 0) {
        amount += field[(y - 1) * x + i];
        amount += field[i + x];
        if (i == 0) {
            amount += field[y * x - 1];
            amount += field[x - 1];
            amount += field[x * 2 - 1];
            amount += field[(y - 1) * x];
            amount += field[1];
            amount += field[x + 1];
        } else if (i == x - 1) {
            amount += field[y * x - 2];
            amount += field[x - 2];
            amount += field[x * 2 - 2];
            amount += field[(y - 1) * x];
            amount += field[0];
            amount += field[x];
        } else {
            amount += field[(y - 1) * x + i - 1];
            amount += field[i - 1];
            amount += field[x + i - 1];
            amount += field[(y - 1) * x + i + 1];
            amount += field[i + 1];
            amount += field[x + i + 1];
        }
    } else if (j == y - 1) {
        amount += field[(y - 2) * x + i];
        amount += field[i];
        if (i == 0) {
            amount += field[(y - 1) * x - 1];
            amount += field[y * x - 1];
            amount += field[x - 1];
            amount += field[(y - 2) * x + 1];
            amount += field[(y - 1) * x + 1];
            amount += field[1];
        } else if (i == x - 1) {
            amount += field[(y - 1) * x - 2];
            amount += field[y * x - 2];
            amount += field[x - 2];
            amount += field[(y - 2) * x];
            amount += field[(y - 1) * x];
            amount += field[0];
        } else {
            amount += field[(y - 2) * x + i - 1];
            amount += field[(y - 1) * x + i - 1];
            amount += field[i - 1];
            amount += field[(y - 2) * x + i + 1];
            amount += field[(y - 1) * x + i + 1];
            amount += field[i + 1];
        }
    } else {
        amount += field[(j - 1) * x + i];
        amount += field[(j + 1) * x + i];
        if (i == 0) {
            amount += field[j * x - 1];
            amount += field[(j + 1) * x - 1];
            amount += field[(j + 2) * x - 1];
            amount += field[(j - 1) * x + 1];
            amount += field[j * x + 1];
            amount += field[(j + 1) * x + 1];
        } else if (i == x - 1) {
            amount += field[j * x - 2];
            amount += field[(j + 1) * x - 2];
            amount += field[(j + 2) * x - 2];
            amount += field[(j - 1) * x];
            amount += field[j * x];
            amount += field[(j + 1) * x];
        } else {
            amount += field[(j - 1) * x + i - 1];
            amount += field[j * x + i - 1];
            amount += field[(j + 1) * x + i - 1];
            amount += field[(j - 1) * x + i + 1];
            amount += field[j * x + i + 1];
            amount += field[(j + 1) * x + i + 1];
        }
    }
    return amount;
}

int one_iteration(int y, int x, usint* field) {
    int diff{0};  // amount of differences between iterations
    usint* temp_field = new usint[y * x];
    for (int i = 0; i < y * x; ++i) {
        int n = neighbours(y, x, field, i);
        temp_field[i] = 0;
        if (field[i] == 1 && doSurvive(n))
            temp_field[i] = 1;
        if (field[i] == 0 && doBorn(n))
            temp_field[i] = 1;
        if (temp_field[i] != field[i])
            ++diff;
    }
    for (int i = 0; i < y * x; ++i)
        field[i] = temp_field[i];
    delete[] temp_field;
    std::cout << diff << '\n';
    return diff;
}

}  // namespace

int main(int argc, char* argv[]) {
    srand(time(0));
    int x{0};
    int y{0};
    int delay{0};
    int limit{0};
    if (argc > 4) {
        x = atoi(argv[1]);
        y = atoi(argv[2]);
        delay = atoi(argv[3]);
        limit = atoi(argv[4]);
    } else {
        std::cerr << "Too few arguments\n";
        return 1;
    }
    usint* field = new usint[y * x];
    fill_field(y, x, field);
    print_field(y, x, field);
    for (int i = 0; i < limit; ++i) {
        if (one_iteration(y, x, field) == 0) {
            break;
        }
        std::this_thread::sleep_for(std::chrono::nanoseconds(delay));
        clear_screen();
        std::cout << i << std::endl;
        print_field(y, x, field);
    };
    delete[] field;
    return 0;
}
